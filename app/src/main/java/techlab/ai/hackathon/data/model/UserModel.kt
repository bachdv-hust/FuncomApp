package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author BachDV
 */
class UserModel : Serializable {
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
    var totalCoin: Double = 0.0

    fun nameDisplay() =firstName + " "+ lastName
}