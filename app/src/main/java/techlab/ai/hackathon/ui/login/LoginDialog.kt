package techlab.ai.hackathon.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import techlab.ai.hackathon.R
import techlab.ai.hackathon.common.toast.AppToast
import techlab.ai.hackathon.common.toast.ToastStyle
import techlab.ai.hackathon.data.model.UserModel
import techlab.ai.hackathon.databinding.DialogLoginBinding
import techlab.ai.hackathon.ui.base.BaseDialogFragment
import techlab.ai.hackathon.ui.comment.CommentBottomSheet
import kotlin.math.log

class LoginDialog : BaseDialogFragment(), LoginView {

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
                binding.btnLogin.text = "ĐĂNG KÝ"
            } else {
                binding.tvTitleScreen.text = "ĐĂNG NHẬP TÀI KHOẢN"
                binding.tvNameUser.visibility = View.GONE
                binding.tvNameUser.setText("")
                binding.tvAccount.setText("")
                binding.tvPassword.setText("")
                binding.tvDes.text = "Vui lòng sử dụng MNV của bạn để gia nhập thế giới\nFUNCOIN!"
                binding.tvChangeTypeLogin.text = "Đăng ký ngay"
                binding.tvQues.text = "Bạn chưa có tài khoản?"
                binding.btnLogin.text = "THAM GIA NGAY"
            }
        }

    private lateinit var loginController: LoginController

    private fun checkStateBtnLogin(): Boolean {
        return if (uiIsRegister) {
            !binding.tvPassword.text.isNullOrBlank() && !binding.tvAccount.text.isNullOrBlank() && !binding.tvNameUser.text.isNullOrBlank()
        } else {
            !binding.tvPassword.text.isNullOrBlank() && !binding.tvAccount.text.isNullOrBlank()
        }
    }

    private fun validateBtnLogin() {
        binding.btnLogin.isEnabled = checkStateBtnLogin()
    }

    override fun afterCreatedView(view: View) {
        loginController = LoginController(this)
        uiIsRegister = false
        binding.tvChangeTypeLogin.setOnClickListener {
            uiIsRegister = !uiIsRegister
        }

        binding.tvAccount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                validateBtnLogin()
            }
        })

        binding.tvPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                validateBtnLogin()
            }
        })

        binding.tvNameUser.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                validateBtnLogin()
            }
        })

        binding.btnLogin.setOnClickListener {
            if (uiIsRegister) {
                loginController.register(binding.tvAccount.text.toString(),binding.tvPassword.text.toString(),binding.tvNameUser.text.toString())
            } else {
                loginController.login(binding.tvAccount.text.toString(),binding.tvPassword.text.toString())
            }
        }

        binding.btnExit.setOnClickListener {
            dismiss()
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

    override fun registerSuccess(user: UserModel) {
        AppToast.createToast(ToastStyle.DONE).setText("Đăng kí thành công").show(requireContext())
        AppToast.createToast(ToastStyle.DONE)
            .setText("Chào mừng ${user.firstName + " " + user.lastName} đến với Funcom!")
            .show(requireContext())
        dismiss()
    }

    override fun registerFail(message: String) {
        AppToast.createToast(ToastStyle.ERROR).setText(message).show(requireContext())
    }

    override fun loginSuccess(user: UserModel) {
        AppToast.createToast(ToastStyle.DONE).setText("Đăng nhập thành công").show(requireContext())
        AppToast.createToast(ToastStyle.DONE)
            .setText("Chào mừng ${user.firstName + " " + user.lastName} đến với Funcom!")
            .show(requireContext())
        dismiss()
    }

    override fun loginFail(message: String) {
        AppToast.createToast(ToastStyle.ERROR).setText(message).show(requireContext())
    }

}