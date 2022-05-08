package techlab.ai.hackathon.ui.multi_choice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import techlab.ai.hackathon.R
import techlab.ai.hackathon.data.model.EventDetail
import techlab.ai.hackathon.data.model.QuestionData
import techlab.ai.hackathon.data.model.QuestionModle
import techlab.ai.hackathon.data.model.ResultQuestionModle
import techlab.ai.hackathon.data.response.MultichoiceResponse
import techlab.ai.hackathon.databinding.ActivityMultiChoiceBinding
import techlab.ai.hackathon.ui.base.BaseActivity
import kotlin.coroutines.CoroutineContext


class MultiChoiceActivity : BaseActivity() {
    private lateinit var multiChoiceBinding: ActivityMultiChoiceBinding
    private lateinit var adapter : MultichoiceAdapter
    private var rcv : RecyclerView? = null
    private lateinit var tvNumQuestion : TextView
    private var eventDetail : EventDetail? = null
    private var numQuestion : Int = 0
    private lateinit var resultQuestionModle : ResultQuestionModle
    private var setAnswer : HashMap<Int,QuestionData>? = null
    private var setAnswerCorrect : MutableSet<Int>? = null
    private var setAnswerFalse : MutableSet<Int>? = null
    private var listQuestionData : MutableList<QuestionData>? = mutableListOf()
    override fun initBindingView(): View {
        multiChoiceBinding = ActivityMultiChoiceBinding.inflate(layoutInflater)
        return multiChoiceBinding.root
    }

    override fun afterCreatedView() {
        intent?.let {
            eventDetail = it.getSerializableExtra("data") as EventDetail
        }
        setAnswer = hashMapOf()
        resultQuestionModle = ResultQuestionModle()
        setAnswerCorrect = mutableSetOf()
        setAnswerFalse = mutableSetOf()
        rcv = multiChoiceBinding.rcvMultichoice
        tvNumQuestion = multiChoiceBinding.tvNumQuestion
        rcv?.layoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false)
        rcv?.hasFixedSize()
        adapter = MultichoiceAdapter(object : OnQuestionClick{
            override fun questionClick(questionData: QuestionData,position : Int) {
                if (questionData.isCheck) {
                    questionData.idAnswer?.let { setAnswer?.put(it,questionData) }
                    if (questionData.answer == questionData.correct) {
                        if (!setAnswerCorrect!!.contains(questionData.idAnswer)){
                            questionData.idAnswer?.let { setAnswerCorrect?.add(it) }
                        }

                    } else {
                        if (setAnswerCorrect!!.contains(questionData.idAnswer)){
                            questionData.idAnswer?.let { setAnswerCorrect?.remove(it) }
                        }

                    }
                }
                resetChoice(questionData,position)

                tvNumQuestion.text = setAnswer?.size.toString() + "/" + numQuestion.toString()
                checkEnableButton()
            }

        })
        eventDetail?.let { sortQuestion(it) }?.let { adapter.setData(it) }
        checkEnableButton()
        rcv?.adapter = adapter
        multiChoiceBinding.btnConfirmMultiChoice.setOnClickListener {
            if (setAnswerCorrect!!.size == numQuestion) {
                ResultQuestionDialogSuccess().newInstance(resultQuestionModle)?.show(supportFragmentManager,"ResultQuestionDialogSuccess")
            } else {
                resultQuestionModle.total_question = numQuestion
                resultQuestionModle.total_question_correct = setAnswerCorrect?.size ?: 0
                resultQuestionModle.total_question_false = numQuestion - setAnswerCorrect?.size!!
                ResultQuestionDialog().newInstance(resultQuestionModle)?.show(supportFragmentManager,"")
                adapter.checkResult()
            }

        }

        multiChoiceBinding.icBackMultiChoice.setOnClickListener {
            onBackPressed()
        }
        tvNumQuestion.text = "0/" + numQuestion.toString()
    }

    fun checkEnableButton() {
        multiChoiceBinding.btnConfirmMultiChoice.isEnabled = setAnswer?.size!! == numQuestion
    }


    fun resetChoice(questionData: QuestionData,position : Int) : List<QuestionData>?{
        listQuestionData?.forEachIndexed { index, qt ->
            if (qt.idAnswer == questionData.idAnswer) {
                if (index != position && questionData.isCheck) {
                    qt.isCheck = false
                    adapter.notifyItemChanged(index,"reset")
                }
            }
        }
//        rcv?.post(object : Runnable{
//            override fun run() {
//                listQuestionData?.let { adapter.setResult(it) }
//            }
//
//        })
        return listQuestionData
    }

    fun sortQuestion(question: EventDetail) : List<QuestionData>{
        val listQuestion = mutableListOf<QuestionData>()
        question.questions.forEachIndexed { index, questionModle ->
            val questionData : QuestionData = QuestionData()
            numQuestion += 1
            if (questionModle.question?.isNotEmpty() == true) {
                questionData.type = 0
                questionData.idAnswer = index
                questionData.question = questionModle.question
                questionData.correct = questionModle.correct
                listQuestion.add(questionData)
            }
            questionModle.answer.forEachIndexed { i, s ->
                val questionData : QuestionData = QuestionData()
                questionData.type = 1
                questionData.idAnswer = index
                questionData.answer = s
                questionData.correct = questionModle.correct
                listQuestion.add(questionData)
            }
        }
        listQuestionData?.addAll(listQuestion)
        return listQuestion
    }
}