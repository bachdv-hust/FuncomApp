package techlab.ai.hackathon.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import techlab.ai.hackathon.R
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.data.model.UserModel
import techlab.ai.hackathon.databinding.ActivityProfileBinding
import techlab.ai.hackathon.share_ui.avatagen.AvatarGenerator
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.main.MainActivity
import techlab.ai.hackathon.ui.manager.AppLoginManager
import techlab.ai.hackathon.ui.manager.CoinUtil
import techlab.ai.hackathon.ui.manager.LoginUtil

class ProfileActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun startSelf(context: Context) {
            val starter = Intent(context, ProfileActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var binding: ActivityProfileBinding

    override fun initBindingView(): View {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun afterCreatedView() {
        binding.btnLogout.setOnClickListener {
            AppLoginManager.isLogged = false
            LoginUtil.setLogout()
            finish()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        updateUI()
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
}