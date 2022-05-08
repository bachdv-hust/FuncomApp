package techlab.ai.hackathon.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import techlab.ai.hackathon.R
import techlab.ai.hackathon.data.model.FeedMenuModel
import techlab.ai.hackathon.databinding.ItemFeedMenuBinding

/**
 * @author BachDV
 */
class FeedMenuAdapter(private val callBack: FeedMenuCallBack) : RecyclerView.Adapter<FeedMenuViewHolder>() {

    var listData: List<FeedMenuModel> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedMenuViewHolder {
        return FeedMenuViewHolder(
            ItemFeedMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedMenuViewHolder, position: Int) {
        holder.bindData(listData[position],callBack)
    }

    override fun getItemCount(): Int = listData.size
}

class FeedMenuViewHolder(private val binding: ItemFeedMenuBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: FeedMenuModel, callBack: FeedMenuCallBack) {
        binding.root.text = item.name ?: ""
        if (item.isSelected){
            binding.root.setTextColor(ContextCompat.getColor(itemView.context,R.color.color_FAB600))
        }else{
            binding.root.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
        }
        binding.root.setOnClickListener {
            callBack.onClick(item)
        }
    }
}

interface FeedMenuCallBack{
    fun onClick(item : FeedMenuModel)
}