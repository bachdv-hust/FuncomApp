package techlab.ai.hackathon.ui.funshop

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import techlab.ai.hackathon.R
import techlab.ai.hackathon.common.coinFormat
import techlab.ai.hackathon.common.toast.AppToast
import techlab.ai.hackathon.common.toast.ToastStyle
import techlab.ai.hackathon.data.model.DonateModel
import techlab.ai.hackathon.data.model.ShopPackage
import techlab.ai.hackathon.databinding.ActivityFunShopBinding
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.manager.CoinUtil

class FunShopActivity : BaseActivity(), FunShopView {

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
    private lateinit var funShopAdapter: FunShopAdapter
    private lateinit var funDonateAdapter: FunDonateAdapter

    override fun afterCreatedView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            val params = binding.toolbar.layoutParams as LinearLayout.LayoutParams
            params.setMargins(0, insets.top, 0, 0)
            binding.toolbar.layoutParams = params
            WindowInsetsCompat.CONSUMED
        }
        funShopController = FunShopController(this)
        funShopAdapter = FunShopAdapter()
        funDonateAdapter = FunDonateAdapter()
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
        binding.tvTotalCoin.text = CoinUtil.getCurrentCoin().coinFormat()
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnShop.setOnClickListener {
            binding.btnShop.setTextColor(ContextCompat.getColor(this, R.color.Branding_Secondary))
            binding.btnDonate.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.rvList.apply {
                layoutManager = LinearLayoutManager(this@FunShopActivity)
                adapter = funShopAdapter
            }
            funShopController.getListPackage()
        }
        binding.btnDonate.setOnClickListener {
            binding.btnShop.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnDonate.setTextColor(ContextCompat.getColor(this, R.color.Branding_Secondary))
            binding.rvList.apply {
                layoutManager = LinearLayoutManager(this@FunShopActivity)
                adapter = funDonateAdapter
            }
            funShopController.getDonates()
        }
        showDialog()
        onRefresh()
    }

    private fun onRefresh() {
        funShopController.getListPackage()
    }

    override fun onResultPackage(packages: List<ShopPackage>) {
        hideDialog()
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

    override fun onResultDonatePackage(packages: List<DonateModel>) {
        hideDialog()
        funDonateAdapter.listData = packages
    }
}