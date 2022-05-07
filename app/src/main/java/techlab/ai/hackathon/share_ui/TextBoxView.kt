package techlab.ai.hackathon.share_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import techlab.ai.hackathon.R

/**
 * @author BachDV
 */
class TextBoxView : FrameLayout {



    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int,defStyleRes:Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initView(context)
    }

    private lateinit var btnSend : View
    private lateinit var edtCmt : EditText
    private fun initView(context: Context) {
       LayoutInflater.from(context).inflate(R.layout.view_text_box, this)
        btnSend = findViewById(R.id.btnSend)
        edtCmt = findViewById(R.id.edtCmt)
    }

    fun setBtnSendOnClick(onclick : OnClickListener){
        btnSend.setOnClickListener(onclick)
    }

    fun  getEdtCmt():EditText{
        return edtCmt
    }
}