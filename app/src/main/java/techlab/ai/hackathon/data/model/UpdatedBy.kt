package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName


data class UpdatedBy(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("avatar") var avatar: String? = null

)