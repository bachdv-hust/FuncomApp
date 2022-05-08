package techlab.ai.hackathon.ui.login

import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.ui.base.BaseController
import techlab.ai.hackathon.ui.manager.AppLoginManager
import techlab.ai.hackathon.ui.manager.LoginUtil

/**
 * @author BachDV
 */
class LoginController(private val loginView: LoginView) : BaseController() {

    fun register(userName : String, password : String, name : String) {
        addDisposable(
            retrofit.register(userName,password,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let { model ->
                            loginView.registerSuccess(model)
                            LoginUtil.setLogin(model)

                        }
                        if (it.isFail()){
                            loginView.registerFail(it.message.toString())
                        }
                    }, {
                        it.printStackTrace()
                        LoginUtil.setLogout()
                    }
                ))
    }


    fun login(userName: String, password: String) {
        addDisposable(
            retrofit.login(userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let { model ->
                            LoginUtil.setLogin(model)
                            loginView.loginSuccess(model)
                        }
                        if (it.isFail() ){
                            loginView.loginFail("")
                        }
                    }, {
                        it.printStackTrace()
                        LoginUtil.setLogout()
                        loginView.loginFail(it.message.toString())
                    }
                ))
    }

}