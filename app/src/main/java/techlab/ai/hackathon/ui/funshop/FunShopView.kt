package techlab.ai.hackathon.ui.funshop

import techlab.ai.hackathon.data.model.ShopPackage

/**
 * @author BachDV
 */
interface FunShopView {
    fun onResultPackage(packages :List<ShopPackage>)
    fun getPackageFail()
}