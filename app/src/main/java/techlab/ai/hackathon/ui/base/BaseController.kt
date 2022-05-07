package techlab.ai.hackathon.ui.base

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import techlab.ai.hackathon.MyApplication
import techlab.ai.hackathon.network.ApiClient

/**
 * @author BachDV
 */
open class BaseController {

    val retrofit = ApiClient.getInstance(MyApplication.mContext)
    private val subscriptions = CompositeDisposable()

    fun addDisposable(d: Disposable) {
        subscriptions.add(d)
    }

    fun clear() {
        subscriptions.clear()
    }
}