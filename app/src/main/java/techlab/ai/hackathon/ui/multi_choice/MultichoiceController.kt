package techlab.ai.hackathon.ui.multi_choice

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import techlab.ai.hackathon.ui.base.BaseController

class MultichoiceController(val multichoiceView: MultichoiceView) : BaseController() {
    fun joinEvent(eventId: Long) {
        addDisposable(retrofit.joinEvent(eventId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.success) {
                    multichoiceView.joinEventSuccess(it.message)
                } else {
                    it.error.message?.let { it1 -> multichoiceView.joinEventFail(it1) }
                }
            },{

            }))
    }
}