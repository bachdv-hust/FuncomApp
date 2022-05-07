package techlab.ai.hackathon.ui.main.adapter

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
        holder.bindData(NewFeed())
    }

    override fun getItemCount(): Int = 10
}

class NewFeedViewHolder( val binding: ItemNewFeedBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(item : NewFeed){
        binding.ivContent.load("https://scontent.fhan3-2.fna.fbcdn.net/v/t39.30808-6/278795851_492996349243829_6497627413104981301_n.jpg?_nc_cat=107&ccb=1-6&_nc_sid=5cd70e&_nc_ohc=Ip2zT0inqzQAX82yJMB&_nc_ht=scontent.fhan3-2.fna&oh=00_AT9gLnFgScnXy1iMjFlm7Rn62QZ4tIeSYDXDZ46-cKHnUg&oe=627A583C")
    }
}