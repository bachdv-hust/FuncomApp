package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author BachDV
 */
class FeedMenuModel {

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("name")
    var name: String? = null

    var isSelected = false
}