package techlab.ai.hackathon.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import techlab.ai.hackathon.R
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.common.pushdown.PushDownAnim
import techlab.ai.hackathon.data.model.NewFeed
import techlab.ai.hackathon.data.model.UserModel
import techlab.ai.hackathon.databinding.ItemNewFeedBinding
import techlab.ai.hackathon.ui.event_detail.EventDetailActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author BachDV
 */
class NewFeedAdapter : RecyclerView.Adapter<NewFeedViewHolder>() {

    var listData: List<NewFeed> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFeedViewHolder {
        return NewFeedViewHolder(
            ItemNewFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewFeedViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}

class NewFeedViewHolder(private val binding: ItemNewFeedBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: NewFeed) {
        try {
            binding.ivContent.load(url = item.thumbnailUrl)
            binding.tvTitle.text = item.title ?: ""
            binding.tvTotalCoin.text = (item.totalFunCoin ?: 0).toInt().toString()
            binding.tvJoined.visibility = if (item.isUserJoined) View.VISIBLE else View.GONE
            binding.tvEndTime.visibility = View.VISIBLE
            binding.tvPersonCount.text = "${item.userJoinedCount} người đã tham gia"
            showEndTime(item = item, isEndedTime = item.timeEnd?.before(Date()) ?: true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        PushDownAnim.setPushDownAnimTo(binding.ivContent).setOnClickListener {
            val user = Gson().fromJson(SharePref.userModel, UserModel::class.java)
            EventDetailActivity.startActivity(
                binding.ivContent.context,
                item.id.toString(),
                user?.id.toString()?:""
            )
        }
    }

    private fun showEndTime(item: NewFeed, isEndedTime: Boolean) {
        if (isEndedTime) {
            binding.tvEndTime.text = "ĐÃ KẾT THÚC"
            binding.tvEndTime.background =
                ContextCompat.getDrawable(binding.root.context, R.drawable.bg_event_ended)
        } else {
            item.timeEnd?.let { date ->
                val dateFormat: DateFormat = SimpleDateFormat("hh:mm - dd/MM/yyyy")
                val strDate: String = dateFormat.format(date)
                binding.tvEndTime.text = strDate
            } ?: kotlin.run {
                binding.tvEndTime.visibility = View.GONE
            }
            binding.tvEndTime.background =
                ContextCompat.getDrawable(binding.root.context, R.drawable.bg_tv_endtime)
        }
    }
}