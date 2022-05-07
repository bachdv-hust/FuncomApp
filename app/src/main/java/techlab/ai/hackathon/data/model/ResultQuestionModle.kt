package techlab.ai.hackathon.data.model

import java.io.Serializable

data class ResultQuestionModle(var total_question :Int? = 0,
                               var total_question_correct :Int? = 0,
                               var total_question_false :Int? = 0) : Serializable {

}