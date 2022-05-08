package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DonateModel(

    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String? = null,
    @SerializedName("total_fun_coin") var total_fun_coin: Int? = null,
    @SerializedName("thumbnail_url") var thumbnail_url: String? = null,
    @SerializedName("supported_fun_coins") var supported_fun_coins: Int? = null

) : Serializable