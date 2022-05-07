package techlab.ai.hackathon.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import techlab.ai.hackathon.R
import techlab.ai.hackathon.databinding.DialogLoginBinding
import techlab.ai.hackathon.ui.base.BaseDialogFragment

class LoginDialog : BaseDialogFragment() {

    private lateinit var binding : DialogLoginBinding
    override fun initBindingView(): View {
        binding = DialogLoginBinding.inflate(LayoutInflater.from(requireContext()),null,false)
       return binding.root
    }

    override fun afterCreatedView(view: View) {
        TODO("Not yet implemented")
    }

}