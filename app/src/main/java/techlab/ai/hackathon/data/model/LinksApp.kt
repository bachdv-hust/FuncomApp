package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class LinksApp(

    @SerializedName("package") var packageApp: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("platform") var platform: String? = null,


) : Serializable