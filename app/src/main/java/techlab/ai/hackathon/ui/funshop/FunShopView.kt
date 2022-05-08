package techlab.ai.hackathon.ui.funshop

import techlab.ai.hackathon.data.model.DonateModel
import techlab.ai.hackathon.data.model.ShopPackage

/**
 * @author BachDV
 */
interface FunShopView {
    fun onResultPackage(packages :List<ShopPackage>)
    fun onResultDonatePackage(packages :List<DonateModel>)
}