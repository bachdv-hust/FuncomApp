package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author BachDV
 */
class CommentModel {
    @SerializedName("id")
    var id: Long = 0


    @SerializedName("user")
    var user: UserModel ?= null

    @SerializedName("content")
    var content: String ?= null

}