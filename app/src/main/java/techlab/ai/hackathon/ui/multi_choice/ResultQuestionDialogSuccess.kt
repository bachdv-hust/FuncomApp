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

class ResultQuestionDialogSuccess() : DialogFragment() {
    var tv_coin : TextView? = null
    var btn_give : TextView? = null

    fun newInstance(resultQuestionModle: ResultQuestionModle): ResultQuestionDialogSuccess? {
        val frag = ResultQuestionDialogSuccess()
        val args = Bundle()
        args.putSerializable("data", resultQuestionModle)
        frag.setArguments(args)
        return frag
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_success_question, container)
        initView(view)
        return view
    }

    fun initView(view: View) {
        tv_coin = view.findViewById(R.id.tv_coin)
        btn_give = view.findViewById(R.id.btn_give_coin)
        btn_give?.setOnClickListener {

        }
    }


}