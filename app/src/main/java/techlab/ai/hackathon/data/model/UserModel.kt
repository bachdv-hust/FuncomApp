package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author BachDV
 */
class UserModel {
    @SerializedName("id")
    var id: Long = 0

    @SerializedName("first_name")
    var firstName: String? = null

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("avatar")
    var avatar: String? = null

    @SerializedName("token")
    var token: String? = null

    @SerializedName("fun_coin")
    var totalCoin: Float = 0f

    fun nameDisplay() =firstName + " "+ lastName
}