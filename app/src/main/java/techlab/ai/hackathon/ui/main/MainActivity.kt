package techlab.ai.hackathon.ui.main

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import techlab.ai.hackathon.R
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.common.pushdown.PushDownAnim
import techlab.ai.hackathon.common.toast.AppToast
import techlab.ai.hackathon.common.toast.ToastStyle
import techlab.ai.hackathon.data.model.NewFeed
import techlab.ai.hackathon.data.model.UserModel
import techlab.ai.hackathon.databinding.ActivityMainBinding
import techlab.ai.hackathon.share_ui.avatagen.AvatarGenerator
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.funshop.FunShopActivity
import techlab.ai.hackathon.ui.login.LoginDialog
import techlab.ai.hackathon.ui.main.adapter.NewFeedAdapter
import techlab.ai.hackathon.ui.manager.*
import techlab.ai.hackathon.ui.profile.ProfileActivity

class MainActivity : BaseActivity(), MainView, LoginChangedListener , CoinChangeListener {

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
        AppCoinManager.addListener(this)
        newFeedAdapter = NewFeedAdapter()
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newFeedAdapter
        }

        binding.ivAvatar.setOnClickListener {
            if (validateLogin()){
                ProfileActivity.startSelf(this)
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            refreshHandler.postDelayed({
                binding.refreshLayout.isRefreshing = false
            }, 1000)
            onRefresh()
        }

        PushDownAnim.setPushDownAnimTo(binding.btnFunShop).setOnClickListener {
            if (validateLogin()){
                FunShopActivity.startSelf(this)
            }
        }
        // Call Api
        showDialog()
        updateUI()
        onRefresh()
    }
    private fun validateLogin() : Boolean{
        return if (SharePref.isLogin){
            true
        }else{
            LoginDialog.show(supportFragmentManager)
            false
        }
    }

    private fun updateUI() {
        val userString = SharePref.userModel
        if (userString.isNotEmpty() && SharePref.isLogin) {
            val user = Gson().fromJson(userString, UserModel::class.java)
            user.avatar?.let {
                binding.ivAvatar.load(url = it, placeholder = R.drawable.ic_logo_funtap)
            }?: kotlin.run {
                val av = AvatarGenerator.AvatarBuilder(this)
                    .setLabel(user?.lastName ?: "")
                    .setAvatarSize(120)
                    .setTextSize(30)
                    .toSquare()
                    .toCircle()
                    .build()
                binding.ivAvatar.setImageDrawable(
                    av
                )
            }

        } else {
            binding.ivAvatar.load(url = "", placeholder = R.drawable.ic_logo_funtap)
        }
        binding.tvTotalCoin.text = CoinUtil.getCurrentCoin().toString()
    }

    private fun onRefresh() {
        mainController.refreshDataNewFeed()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainController.clear()
        AppLoginManager.removeListener(this)
        AppCoinManager.removeListener(this)
    }

    override fun onNewFeedsResult(newFeeds: List<NewFeed>) {
        hideDialog()
        newFeedAdapter.listData = newFeeds
    }

    override fun onFail() {
        hideDialog()
        AppToast.createToast(ToastStyle.ERROR).setText("Đã có lỗi xảy ra!").show(this)
    }

    override fun onLoginChanged(isLogged: Boolean) {
        updateUI()
        onRefresh()
    }

    override fun onChange(totalCoin: Float) {
        binding.tvTotalCoin.text = totalCoin.toString()
    }


}