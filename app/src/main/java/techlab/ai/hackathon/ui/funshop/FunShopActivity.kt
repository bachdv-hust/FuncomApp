package techlab.ai.hackathon.ui.funshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.common.toast.AppToast
import techlab.ai.hackathon.common.toast.ToastStyle
import techlab.ai.hackathon.data.model.ShopPackage
import techlab.ai.hackathon.data.model.UserModel
import techlab.ai.hackathon.databinding.ActivityFunShopBinding
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.manager.CoinUtil

class FunShopActivity : BaseActivity(),FunShopView {

    companion object {
        fun startSelf(context: Context) {
            val starter = Intent(context, FunShopActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var binding: ActivityFunShopBinding

    override fun initBindingView(): View {
        binding = ActivityFunShopBinding.inflate(layoutInflater)
        return binding.root
    }
    private lateinit var funShopController: FunShopController
    private val refreshHandler = Handler(Looper.getMainLooper())
    private lateinit var funShopAdapter : FunShopAdapter

    override fun afterCreatedView() {
        funShopController = FunShopController(this)
        funShopAdapter = FunShopAdapter()
        binding.refreshLayout.setOnRefreshListener {
            refreshHandler.postDelayed({
                binding.refreshLayout.isRefreshing = false
            }, 1000)
            onRefresh()
        }
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@FunShopActivity)
            adapter = funShopAdapter
        }
        binding.tvTotalCoin.text = CoinUtil.getCurrentCoin().toString()
        binding.btnBack.setOnClickListener {
            finish()
        }
        showDialog()
        onRefresh()
    }

    private fun onRefresh() {
        funShopController.getListPackage()
    }

    override fun onResultPackage(packages: List<ShopPackage>) {
        funShopAdapter.listData = packages
        hideDialog()
    }

    override fun getPackageFail() {
        hideDialog()
        AppToast.createToast(ToastStyle.ERROR).setText("Có lỗi xảy ra!").show(this)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideDialog()
        funShopController.clear()
    }
}