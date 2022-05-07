package techlab.ai.hackathon.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.data.model.NewFeed
import techlab.ai.hackathon.databinding.ItemNewFeedBinding

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

class NewFeedViewHolder(val binding: ItemNewFeedBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: NewFeed) {
        binding.ivContent.load(url = item.thumbnailUrl)
        binding.tvTitle.text = item.title ?: ""
        binding.tvTotalCoin.text = (item.totalFunCoin ?: 0).toInt().toString()
    }
}