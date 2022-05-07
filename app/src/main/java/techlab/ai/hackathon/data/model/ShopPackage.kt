package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author BachDV
 */
class ShopPackage {

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("thumbnail_url")
    var thumbnailUrl: String? = null

}