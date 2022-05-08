package techlab.ai.hackathon.ui.comment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Comment
import techlab.ai.hackathon.R
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.data.model.CommentModel
import techlab.ai.hackathon.databinding.ItemCommentBinding
import techlab.ai.hackathon.share_ui.avatagen.AvatarGenerator

/**
 * @author BachDV
 */
class CommentAdapter : ListAdapter<CommentModel, CommentViewHolder>(CommentDiffCallBack()) {

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
        holder.bindData(getItem(position))
    }
}

private class CommentDiffCallBack : DiffUtil.ItemCallback<CommentModel>() {
    override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
        return false
    }
}

class CommentViewHolder(private val binding: ItemCommentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: CommentModel) {
        try {
            binding.tvContent.text = item.content
            item.user?.let { user ->
                binding.tvName.text = (user.firstName ?: "") + (user.lastName ?: "")
                user.avatar?.let {
                    binding.ivAvatar.load(url = it, placeholder = R.drawable.ic_logo_funtap)
                } ?: kotlin.run {
                    val av = AvatarGenerator.AvatarBuilder(binding.root.context)
                        .setLabel(user?.lastName ?: "")
                        .setAvatarSize(120)
                        .setTextSize(30)
                        .toSquare()
                        .toCircle()
                        .build()
                    binding.ivAvatar.setImageDrawable(
                        av
                    )
                }
            } ?: kotlin.run {
                binding.tvName.text = ""
            }


        } finally {

        }

    }
}