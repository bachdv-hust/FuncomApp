package techlab.ai.hackathon.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import techlab.ai.hackathon.R
import techlab.ai.hackathon.databinding.ActivityEventDetailBinding
import techlab.ai.hackathon.ui.comment.CommentActivity
import techlab.ai.hackathon.ui.comment.CommentBottomSheet

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.layout.tvMore.setOnClickListener {
            CommentActivity.startSelf(this, 1)
        }
    }
}