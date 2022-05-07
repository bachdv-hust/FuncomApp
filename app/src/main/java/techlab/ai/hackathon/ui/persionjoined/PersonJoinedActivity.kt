package techlab.ai.hackathon.ui.persionjoined

import android.content.Context
import android.content.Intent
import android.view.View
import techlab.ai.hackathon.databinding.ActivityPersonJoinedBinding
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.event_detail.EventDetailActivity

class PersonJoinedActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context, eventId: String) {
            val intent = Intent(context, PersonJoinedActivity::class.java)
            intent.putExtra("eventId", eventId)
            context.startActivity(intent)
        }
    }
    private lateinit var binding: ActivityPersonJoinedBinding

    override fun initBindingView(): View {
        binding = ActivityPersonJoinedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun afterCreatedView() {
        binding.ivBack.setOnClickListener {
            finish()
        }


    }
}