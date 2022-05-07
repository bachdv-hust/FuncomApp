package techlab.ai.hackathon.ui.event_detail

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import techlab.ai.hackathon.ui.base.BaseController

/**
 * @author BachDV
 */
class EventDetailController(private val eventDetailView: EventDetailView) : BaseController() {

    fun getEventDetail(eventId: String, userId: String) {
        addDisposable(
            retrofit.getEventDetail(eventId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let { model ->
                            eventDetailView.onEventDetailResult(model)
                        }
                    }, {
                        it.printStackTrace()
                    }
                )
        )
    }

}