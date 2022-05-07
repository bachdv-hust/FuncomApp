package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author BachDV
 */
class NewFeed {

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("type")
    var type: Int = 0

    @SerializedName("total_fun_coin")
    var totalFunCoin: Double? = null

    @SerializedName("remaining_fun_coin")
    var remainingFunCoin: Double? = null

    @SerializedName("time_start")
    var timeStart: Date? = null

    @SerializedName("time_end")
    var timeEnd: Date? = null

    @SerializedName("thumbnail_url")
    var thumbnailUrl: String? = null

}