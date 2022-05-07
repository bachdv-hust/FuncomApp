package techlab.ai.hackathon.ui.persionjoined

import android.view.View
import techlab.ai.hackathon.databinding.ActivityPersonJoinedBinding
import techlab.ai.hackathon.ui.base.BaseActivity

class PersonJoinedActivity : BaseActivity() {


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