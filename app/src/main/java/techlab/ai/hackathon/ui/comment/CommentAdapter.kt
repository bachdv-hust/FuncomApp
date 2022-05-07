package techlab.ai.hackathon.ui.comment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Comment
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.data.model.CommentModel
import techlab.ai.hackathon.databinding.ItemCommentBinding

/**
 * @author BachDV
 */
class CommentAdapter : RecyclerView.Adapter<CommentViewHolder>() {

    var listData: List<CommentModel> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}

class CommentViewHolder(private val binding: ItemCommentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: CommentModel) {
        try {
            binding.ivAvatar.load(url = item.content)
            binding.tvContent.text = item.content
            item.user?.let { user ->
                binding.tvName.text = user.firstName ?: "" + user.lastName ?: ""
            } ?: kotlin.run {
                binding.tvName.text = ""
            }
        } finally {

        }

    }
}