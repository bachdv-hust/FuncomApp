package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Highlight(

    @SerializedName("title") var title: String? = null,

) : Serializable