package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName


data class Links(

    @SerializedName("name") var name: String? = null,
    @SerializedName("link") var link: String? = null,


)