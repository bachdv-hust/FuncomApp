package techlab.ai.hackathon.ui.persionjoined

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import techlab.ai.hackathon.data.model.EventDetail
import techlab.ai.hackathon.data.model.UserJoin
import techlab.ai.hackathon.databinding.ActivityPersonJoinedBinding
import techlab.ai.hackathon.ui.base.BaseActivity

class PersonJoinedActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context, eventDetail: EventDetail?) {
            eventDetail?.let {
                val intent = Intent(context, PersonJoinedActivity::class.java)
                intent.putExtra("eventDetail", Gson().toJson(it))
                context.startActivity(intent)
            }
        }
    }

    private lateinit var binding: ActivityPersonJoinedBinding
    override fun initBindingView(): View {
        binding = ActivityPersonJoinedBinding.inflate(layoutInflater)
        return binding.root
    }

    private lateinit var personJoinedAdapter: PersonJoinedAdapter

    override fun afterCreatedView() {
        personJoinedAdapter = PersonJoinedAdapter()
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@PersonJoinedActivity)
            adapter = personJoinedAdapter
        }
        val eventDetailString = intent?.getStringExtra("eventDetail")
        eventDetailString?.let { data ->
            val eventDetail = Gson().fromJson(data, EventDetail::class.java)
            eventDetail?.userJoined?.let {
                personJoinedAdapter.listData = it
            }

            val remainingFunCoin = eventDetail.remainingFunCoin ?: 0
            val totalFunCoin = eventDetail.totalFunCoin ?: 0
            if (totalFunCoin <= 0) {
                binding.progressCoin.setProgress(0)
            } else {
                val percentRemain = (remainingFunCoin / totalFunCoin) * 100
                binding.progressCoin.setMaxProgress(totalFunCoin)
                binding.progressCoin.setProgress(totalFunCoin - remainingFunCoin)
            }
            binding.tvCoinCirculating.text = (totalFunCoin - remainingFunCoin).toString()
            binding.tvCoinTotal.text = totalFunCoin.toString()
        }
    }
}