package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class EventDetail(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("type") var type: Int? = null,
    @SerializedName("start_date") var startDate: String? = null,
    @SerializedName("end_date") var endDate: String? = null,
    @SerializedName("total_fun_coin") var totalFunCoin: Int? = null,
    @SerializedName("remaining_fun_coin") var remainingFunCoin: Int? = null,
    @SerializedName("receive_fun_coin") var receiveFunCoin: Int? = null,
    @SerializedName("is_user_joined") var isUserJoined: Boolean? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("thumbnail_url") var thumbnailUrl: String? = null,
    @SerializedName("links") var links: ArrayList<Links> = arrayListOf(),
    @SerializedName("donors") var donors: ArrayList<Donors> = arrayListOf(),
    @SerializedName("highlights") var highlights: ArrayList<Highlight> = arrayListOf(),
    @SerializedName("questions") var questions: ArrayList<Questions> = arrayListOf(),
    @SerializedName("created_by") var createdBy: CreatedBy? = CreatedBy(),
    @SerializedName("updated_by") var updatedBy: UpdatedBy? = UpdatedBy(),
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("user_joined") var userJoined: ArrayList<UserJoin> = arrayListOf()

) : Serializable