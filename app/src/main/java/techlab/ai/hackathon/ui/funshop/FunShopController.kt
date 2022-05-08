package techlab.ai.hackathon.ui.funshop

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import techlab.ai.hackathon.ui.base.BaseController

/**
 * @author BachDV
 */
class FunShopController(private val funShopView: FunShopView) : BaseController() {

    fun getListPackage() {
        addDisposable(
            retrofit.getShopPackage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let { model ->
                            funShopView.onResultPackage(model)
                        }
                    }, {
                        it.printStackTrace()
                    }
                )
        )
    }

    fun getDonates() {
        addDisposable(
            retrofit.getDonates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let { model ->
                            funShopView.onResultDonatePackage(model)
                        }
                    }, {
                        it.printStackTrace()
                    }
                )
        )
    }
}