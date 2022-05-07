package techlab.ai.hackathon.ui.manager

import com.google.gson.Gson
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.data.model.UserModel

/**
 * @author BachDV
 */
class LoginUtil {
    companion object{

        fun setLogin(userModel: UserModel){
            SharePref.userModel = Gson().toJson(userModel)
            SharePref.isLogin = true
            AppLoginManager.isLogged = true
            SharePref.token = userModel.token?:""
        }

        fun setLogout(){
            SharePref.isLogin = false
            AppLoginManager.isLogged = false
        }
    }
}