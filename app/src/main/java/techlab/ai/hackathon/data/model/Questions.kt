package techlab.ai.hackathon.data.model

import com.google.gson.annotations.SerializedName


data class Questions(

    @SerializedName("answer") var answer: ArrayList<String> = arrayListOf(),
    @SerializedName("correct") var correct: String? = null,
    @SerializedName("question") var question: String? = null

)