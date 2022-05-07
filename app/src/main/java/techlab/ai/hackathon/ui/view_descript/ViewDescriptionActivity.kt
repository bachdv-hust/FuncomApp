package techlab.ai.hackathon.ui.view_descript

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import techlab.ai.hackathon.R
import techlab.ai.hackathon.ui.event_detail.EventDetailActivity

class ViewDescriptionActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context, content: String) {
            val intent = Intent(context, ViewDescriptionActivity::class.java)
            intent.putExtra("content", content)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_description)
        val content = intent.getStringExtra("content")
        val ic_back_multi_choice = findViewById<View>(R.id.ic_back_multi_choice)
        ic_back_multi_choice.setOnClickListener { finish() }
        val webView = findViewById<WebView>(R.id.wvDescipt)
        content?.let {
            webView.loadData(it, "text/html; charset=UTF-8", null);
        }

    }
}