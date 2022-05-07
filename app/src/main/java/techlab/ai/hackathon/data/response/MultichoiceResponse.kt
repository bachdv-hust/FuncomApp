package techlab.ai.hackathon.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import techlab.ai.hackathon.data.model.QuestionModle

class MultichoiceResponse {
    @SerializedName("questions")
    @Expose
    val questions: List<QuestionModle>? = null
}