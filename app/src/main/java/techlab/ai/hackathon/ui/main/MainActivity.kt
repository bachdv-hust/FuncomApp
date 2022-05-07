package techlab.ai.hackathon.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import techlab.ai.hackathon.R
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.common.toast.AppToast
import techlab.ai.hackathon.common.toast.ToastStyle
import techlab.ai.hackathon.data.model.DemoModel
import techlab.ai.hackathon.data.model.NewFeed
import techlab.ai.hackathon.data.model.UserModel
import techlab.ai.hackathon.databinding.ActivityMainBinding
import techlab.ai.hackathon.ui.EventDetailActivity
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.login.LoginDialog
import techlab.ai.hackathon.ui.main.adapter.NewFeedAdapter
import techlab.ai.hackathon.ui.manager.AppLoginManager
import techlab.ai.hackathon.ui.manager.LoginChangedListener
import techlab.ai.hackathon.ui.profile.ProfileActivity

class MainActivity : BaseActivity(), MainView, LoginChangedListener {

    companion object {
        @JvmStatic
        fun startSelf(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainController: MainController

    override fun initBindingView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    private lateinit var newFeedAdapter: NewFeedAdapter

    private val refreshHandler = Handler(Looper.getMainLooper())

    override fun afterCreatedView() {
        mainController = MainController(this)
        AppLoginManager.addListener(this)
        newFeedAdapter = NewFeedAdapter()
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newFeedAdapter
        }

        binding.ivAvatar.setOnClickListener {
            if (SharePref.isLogin) {
                ProfileActivity.startSelf(this)
            } else {
                LoginDialog.show(supportFragmentManager)
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            refreshHandler.postDelayed({
                binding.refreshLayout.isRefreshing = false
            }, 1000)
            onRefresh()
        }
        // Call Api
        onRefresh()
    }

    private fun onRefresh() {
        mainController.refreshDataNewFeed()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainController.clear()
        AppLoginManager.removeListener(this)
    }

    override fun onNewFeedsResult(newFeeds: List<NewFeed>) {
        newFeedAdapter.listData = newFeeds
    }

    override fun onLoginChanged(isLogged: Boolean) {
        if (isLogged) {
            val user = Gson().fromJson(SharePref.userModel, UserModel::class.java)
            binding.ivAvatar.load(url = user.avatar, placeholder = R.drawable.ic_logo_funtap)
            binding.tvTotalCoin.text = user.totalCoin.toInt().toString()
        } else {
            binding.ivAvatar.load(url = "", placeholder = R.drawable.ic_logo_funtap)
            binding.tvTotalCoin.text = "0"
        }
        onRefresh()
    }


}