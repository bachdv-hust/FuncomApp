package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName


data class UserJoin(

    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("user") var user: UserModel? = null,
    @SerializedName("fun_coin") var coin: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,


)