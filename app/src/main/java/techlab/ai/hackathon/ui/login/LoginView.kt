package techlab.ai.hackathon.ui.login

import techlab.ai.hackathon.data.model.UserModel

/**
 * @author BachDV
 */
interface LoginView {
    fun registerSuccess(user : UserModel)

    fun registerFail(message: String)

    fun loginSuccess(user : UserModel)

    fun loginFail(message : String)
}