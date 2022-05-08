package techlab.ai.hackathon.ui.main

import android.content.Context
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.ui.base.BaseController
import techlab.ai.hackathon.ui.manager.CoinUtil
import techlab.ai.hackathon.ui.manager.LoginUtil

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

    fun getInfo(){
        addDisposable(
            retrofit.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let {model->
                            SharePref.userModel = Gson().toJson(model)
                            CoinUtil.updateCoin(model.totalCoin)
                        }
                    }, {
                        it.printStackTrace()
                    }
                )
        )
    }

}