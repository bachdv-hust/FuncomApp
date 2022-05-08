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
import techlab.ai.hackathon.data.model.DonateModel
import techlab.ai.hackathon.data.model.ShopPackage
import techlab.ai.hackathon.databinding.ItemFunDonateBinding
import techlab.ai.hackathon.databinding.ItemFunShopBinding

/**
 * @author BachDV
 */
class FunDonateAdapter : RecyclerView.Adapter<FunDonateViewHolder>() {

    var listData: List<DonateModel> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunDonateViewHolder {
        return FunDonateViewHolder(
            ItemFunDonateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FunDonateViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}

class FunDonateViewHolder(private val binding: ItemFunDonateBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: DonateModel) {
        binding.ivContent.loadCorner(
            url = item.thumbnail_url,
            corner = binding.root.context.resources.getDimensionPixelOffset(
                R.dimen.dp_1
            )
        )
        binding.tvTitle.text = item.title?:""
        binding.progressCoin.setMaxProgress(item.total_fun_coin!!)
        binding.progressCoin.setProgress(item.supported_fun_coins!!)
        binding.tvCoinCirculating.text = item.supported_fun_coins.toString()
        binding.tvCoinTotal.text = item.total_fun_coin.toString()
    }
}