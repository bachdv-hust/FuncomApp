package techlab.ai.hackathon.ui.multi_choice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import techlab.ai.hackathon.R
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
    private var numQuestion : Int = 0
    private lateinit var resultQuestionModle : ResultQuestionModle
    private var setAnswer : HashMap<Int,QuestionData>? = null
    private var setAnswerCorrect : MutableSet<QuestionData>? = null
    private var setAnswerFalse : MutableSet<QuestionData>? = null
    private var listQuestionData : MutableList<QuestionData>? = mutableListOf()
    override fun initBindingView(): View {
        multiChoiceBinding = ActivityMultiChoiceBinding.inflate(layoutInflater)
        return multiChoiceBinding.root
    }

    override fun afterCreatedView() {
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
                        setAnswerCorrect?.add(questionData)
                    } else {
                        setAnswerFalse?.add(questionData)
                    }
                }
                resetChoice(questionData,position)

                tvNumQuestion.text = setAnswer?.size.toString() + "/" + numQuestion.toString()
                checkEnableButton()
            }

        })
        val string = "{\n" +
                "\"questions\": [\n" +
                "                {\n" +
                "                    \"answer\": [\n" +
                "                        \"5\",\n" +
                "                        \"10\",\n" +
                "                        \"12\",\n" +
                "                        \"16\"\n" +
                "                    ],\n" +
                "                    \"correct\": \"16\",\n" +
                "                    \"question\": \"Số lượng giải thưởng là bao nhiêu?\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": [\n" +
                "                        \"500.000\",\n" +
                "                        \"1.000.000\",\n" +
                "                        \"2.000.000\",\n" +
                "                        \"5.000.000\"\n" +
                "                    ],\n" +
                "                    \"correct\": \"1.000.000\",\n" +
                "                    \"question\": \"Giải nhất trị giá bao nhiêu?\"\n" +
                "                }\n" +
                "            ]\n" +
                "}"
        val gson = Gson()
        val question = gson.fromJson(string,MultichoiceResponse::class.java)
        adapter.setData(sortQuestion(question))
        checkEnableButton()
        rcv?.adapter = adapter
        multiChoiceBinding.btnConfirmMultiChoice.setOnClickListener {
            if (setAnswerCorrect!!.size == numQuestion) {
                ResultQuestionDialogSuccess().newInstance(resultQuestionModle)?.show(supportFragmentManager,"ResultQuestionDialogSuccess")
            } else {
                resultQuestionModle.total_question = numQuestion
                resultQuestionModle.total_question_correct = setAnswerCorrect?.size ?: 0
                resultQuestionModle.total_question_false = setAnswerFalse?.size!!
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
                if (index != position) {
                    qt.isCheck = false
                }
            }
        }
        rcv?.post(object : Runnable{
            override fun run() {
                listQuestionData?.let { adapter.setResult(it) }
            }

        })
        return listQuestionData
    }

    fun sortQuestion(question: MultichoiceResponse) : List<QuestionData>{
        val listQuestion = mutableListOf<QuestionData>()
        question.questions?.forEachIndexed { index, questionModle ->
            val questionData : QuestionData = QuestionData()
            numQuestion += 1
            if (questionModle.question?.isNotEmpty() == true) {
                questionData.type = 0
                questionData.idAnswer = index
                questionData.question = questionModle.question
                questionData.correct = questionModle.correct
                listQuestion.add(questionData)
            }
            questionModle.answer?.forEachIndexed { i, s ->
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