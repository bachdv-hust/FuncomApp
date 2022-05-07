package techlab.ai.hackathon.share_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import techlab.ai.hackathon.R

class HeaderTitleView : FrameLayout {


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        var text: String?
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.header_title,
            0, 0
        ).apply {
            try {
                text = getString(R.styleable.header_title_text)

            } finally {
                recycle()
            }
        }
        LayoutInflater.from(context).inflate(R.layout.item_detail_header, this)
        val tvTitle = findViewById<TextView>(R.id.tvTitle);
        tvTitle.text = text
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        var text: String?
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.header_title,
            0, 0
        ).apply {
            try {
                text = getString(R.styleable.header_title_text)

            } finally {
                recycle()
            }
        }
        LayoutInflater.from(context).inflate(R.layout.item_detail_header, this)
        val tvTitle = findViewById<TextView>(R.id.tvTitle);
        tvTitle.text = text
    }

    fun setText(text:String){
        val tvTitle = findViewById<TextView>(R.id.tvTitle);
        tvTitle.text = text
    }
}