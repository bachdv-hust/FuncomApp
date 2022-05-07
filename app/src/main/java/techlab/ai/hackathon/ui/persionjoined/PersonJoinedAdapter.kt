package techlab.ai.hackathon.ui.persionjoined

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.data.model.UserJoin
import techlab.ai.hackathon.databinding.ItemUserJoinedBinding

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
        binding.ivUserAvatar.load(url = user.user?.avatar)
        binding.tvUserName.text = user.user?.nameDisplay()
        binding.tvCoin.text = "+${user.coin}"
    }
}