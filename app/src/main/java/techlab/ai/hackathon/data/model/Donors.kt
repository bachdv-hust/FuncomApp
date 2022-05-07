package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName


data class Donors(

    @SerializedName("icon") var icon: String? = null,
    @SerializedName("name") var name: String? = null

)