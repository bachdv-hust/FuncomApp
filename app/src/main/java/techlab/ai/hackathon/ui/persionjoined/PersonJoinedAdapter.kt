package techlab.ai.hackathon.ui.persionjoined

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.data.model.UserJoin
import techlab.ai.hackathon.databinding.ItemUserJoinedBinding
import techlab.ai.hackathon.share_ui.avatagen.AvatarGenerator

/**
 * @author BachDV
 */
class PersonJoinedAdapter : RecyclerView.Adapter<PersonJoinedViewHolder>() {
    var listData: List<UserJoin> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonJoinedViewHolder {
        return PersonJoinedViewHolder(
            ItemUserJoinedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PersonJoinedViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}

class PersonJoinedViewHolder(private val binding: ItemUserJoinedBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(user: UserJoin) {
        user.user?.avatar?.let {
            binding.ivUserAvatar.load(url = it)
        }?: kotlin.run {
            val av = AvatarGenerator.AvatarBuilder(binding.root.context)
                .setLabel(user.user?.lastName ?: "")
                .setAvatarSize(120)
                .setTextSize(30)
                .toSquare()
                .toCircle()
                .build()
            binding.ivUserAvatar.setImageDrawable(
                av
            )
        }
        binding.tvUserName.text = user.user?.nameDisplay()
        binding.tvCoin.text = "+${user.coin}"
    }
}