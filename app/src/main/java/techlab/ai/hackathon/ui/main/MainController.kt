package techlab.ai.hackathon.ui.main

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import techlab.ai.hackathon.ui.base.BaseController

/**
 * @author BachDV
 */
class MainController(private val mainView: MainView) : BaseController() {

    fun refreshDataNewFeed(){
        addDisposable(
            retrofit.getDataNewFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let {model->
                            mainView.onNewFeedsResult(model)
                        }
                    }, {
                        it.printStackTrace()
                        mainView.onFail()
                    }
                )
        )
    }

}