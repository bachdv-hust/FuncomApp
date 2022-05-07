package techlab.ai.hackathon.ui.multi_choice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import okhttp3.internal.userAgent
import techlab.ai.hackathon.R
import techlab.ai.hackathon.data.model.ResultQuestionModle

class ResultQuestionDialog() : DialogFragment() {

    var resultQuestionModle : ResultQuestionModle? = null
    var tv_total : TextView? = null
    var tv_total_correct : TextView? = null
    var tv_total_false : TextView? = null
    var btn_try : TextView? = null

    fun newInstance(resultQuestionModle: ResultQuestionModle): ResultQuestionDialog? {
        val frag = ResultQuestionDialog()
        val args = Bundle()
        args.putSerializable("data", resultQuestionModle)
        frag.setArguments(args)
        return frag
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            resultQuestionModle = getSerializable("data") as ResultQuestionModle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_result_question, container)
        initView(view)
        return view
    }

    fun initView(view: View) {
        tv_total = view.findViewById(R.id.tv_total_question)
        tv_total_correct = view.findViewById(R.id.tv_total_question_correct)
        tv_total_false = view.findViewById(R.id.tv_total_question_false)
        btn_try = view.findViewById(R.id.btn_try)
        tv_total?.text = resultQuestionModle?.total_question.toString()
        tv_total_correct?.text = resultQuestionModle?.total_question_correct.toString()
        tv_total_false?.text = resultQuestionModle?.total_question_false.toString()
        btn_try?.setOnClickListener {
            dismiss()
        }
    }


}