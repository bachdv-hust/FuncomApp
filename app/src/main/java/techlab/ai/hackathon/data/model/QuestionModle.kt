package techlab.ai.hackathon.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class QuestionModle {
    @SerializedName("answer")
    @Expose
     val answer: List<String>? = null

    @SerializedName("correct")
    @Expose
     val correct: String? = null

    @SerializedName("question")
    @Expose
     val question: String? = null
}