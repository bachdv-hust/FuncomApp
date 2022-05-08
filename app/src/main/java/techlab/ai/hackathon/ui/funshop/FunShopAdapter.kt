package techlab.ai.hackathon.ui.funshop

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import techlab.ai.hackathon.R
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.common.loadCorner
import techlab.ai.hackathon.common.pushdown.PushDownAnim
import techlab.ai.hackathon.data.model.ShopPackage
import techlab.ai.hackathon.databinding.ItemFunShopBinding

/**
 * @author BachDV
 */
class FunShopAdapter : RecyclerView.Adapter<FunShopViewHolder>() {
    var listData: List<ShopPackage> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunShopViewHolder {
        return FunShopViewHolder(
            ItemFunShopBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FunShopViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}

class FunShopViewHolder(private val binding: ItemFunShopBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: ShopPackage) {
        binding.ivContent.loadCorner(
            url = item.thumbnailUrl,
            corner = binding.root.context.resources.getDimensionPixelOffset(
                R.dimen.dp_8
            )
        )
        binding.tvTitle.text = item.title?:""
        binding.tvCoin.text = item.funCoin.toString()
        PushDownAnim.setPushDownAnimTo(binding.btnJoinNow).setOnClickListener {

        }
    }
}