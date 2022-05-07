package techlab.ai.hackathon.cached

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import techlab.ai.hackathon.MyApplication
import techlab.ai.hackathon.data.model.UserModel

/**
 * @author BachDV
 */
object SharePref {

    private const val FILE_NAME = "share.data"

    private var prefs: SharedPreferences =
        MyApplication.mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    var token: String by SharePrefDelegate(prefs, "token", "")

    var userName: String by SharePrefDelegate(prefs, "userName", "")

    var isLogin: Boolean by SharePrefDelegate(prefs, "isLogin", false)

    var userModel: String by SharePrefDelegate(prefs, "userModel", "")


    @SuppressLint("CommitPrefEdits")
    fun clear() {
        prefs.edit().clear().apply()
    }
}