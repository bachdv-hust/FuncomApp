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

    @SerializedName("type")
    var type: Int = 0

    @SerializedName("total_fun_coin")
    var totalFunCoin: Double? = null

    @SerializedName("remaining_fun_coin")
    var remainingFunCoin: Double? = null

    @SerializedName("start_date")
    var timeStart: Date? = null

    @SerializedName("end_date")
    var timeEnd: Date? = null

    @SerializedName("thumbnail_url")
    var thumbnailUrl: String? = null

    @SerializedName("is_user_joined")
    var isUserJoined: Boolean = false

    @SerializedName("user_joined_count")
    var userJoinedCount: Int = 0

}