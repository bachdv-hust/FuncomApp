package techlab.ai.hackathon.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import techlab.ai.hackathon.R
import techlab.ai.hackathon.databinding.DialogLoginBinding
import techlab.ai.hackathon.ui.base.BaseDialogFragment
import techlab.ai.hackathon.ui.comment.CommentBottomSheet

class LoginDialog : BaseDialogFragment() {

    private lateinit var binding: DialogLoginBinding
    override fun initBindingView(): View {
        binding = DialogLoginBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        return binding.root
    }

    var uiIsRegister: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.tvTitleScreen.text = "ĐĂNG KÝ TÀI KHOẢN"
                binding.tvNameUser.visibility = View.VISIBLE
                binding.tvNameUser.setText("")
                binding.tvAccount.setText("")
                binding.tvPassword.setText("")
                binding.tvDes.text =
                    "Vui lòng sử dụng MNV của bạn để  đăng ký\ngia nhập thế giới FUNCOIN!"
                binding.tvChangeTypeLogin.text = "Tham gia ngay"
                binding.tvQues.text = "Bạn đã có tài khoản?"
            } else {
                binding.tvTitleScreen.text = "ĐĂNG NHẬP TÀI KHOẢN"
                binding.tvNameUser.visibility = View.GONE
                binding.tvNameUser.setText("")
                binding.tvAccount.setText("")
                binding.tvPassword.setText("")
                binding.tvDes.text = "Vui lòng sử dụng MNV của bạn để gia nhập thế giới\nFUNCOIN!"
                binding.tvChangeTypeLogin.text = "Đăng ký ngay"
                binding.tvQues.text = "Bạn chưa có tài khoản?"
            }
        }


    override fun afterCreatedView(view: View) {
        uiIsRegister = false
        binding.tvChangeTypeLogin.setOnClickListener {
            uiIsRegister = !uiIsRegister
        }
    }

    companion object {

        private fun newInstance(): LoginDialog {
            val args = Bundle()
            val fragment = LoginDialog()
            fragment.arguments = args
            return fragment
        }

        fun show(fragmentManager: FragmentManager) {
            val bottomSheet: LoginDialog = newInstance()
            bottomSheet.show(fragmentManager, "LoginDialog")
        }
    }

}