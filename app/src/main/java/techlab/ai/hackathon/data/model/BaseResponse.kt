package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author BachDV
 */
class BaseResponse<T>(
    @SerializedName("code")
    val code: Int =0 ,
    @SerializedName("error")
    val error: Error,
    @SerializedName("success")
    val success: Boolean = false ,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("data")
    val data: T?
) {
    fun isFail(): Boolean {
        return code != 200 && code!=0
    }
}