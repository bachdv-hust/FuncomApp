package techlab.ai.hackathon.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Error : Serializable {
    @SerializedName("code")
    @Expose
     val code: Int? = null

    @SerializedName("message")
    @Expose
     val message: String? = null

    @SerializedName("errors")
    @Expose
     val errors: List<Any>? = null

}