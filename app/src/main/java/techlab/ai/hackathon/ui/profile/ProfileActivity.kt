package techlab.ai.hackathon.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.databinding.ActivityProfileBinding
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.main.MainActivity
import techlab.ai.hackathon.ui.manager.AppLoginManager
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
    }
}