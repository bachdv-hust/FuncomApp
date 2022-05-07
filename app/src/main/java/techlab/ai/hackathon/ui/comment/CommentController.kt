package techlab.ai.hackathon.ui.comment

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import techlab.ai.hackathon.ui.base.BaseController

/**
 * @author BachDV
 */
class CommentController (private val commentView : CommentView): BaseController() {

    fun getComment(eventId: Long) {
        addDisposable(
            retrofit.getComments(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let {model->
                            commentView.onCommentResult(model)
                        }
                    }, {
                        it.printStackTrace()
                        commentView.onFail()
                    }
                )
        )
    }

    fun postComment(eventId: Long, content : String){
        addDisposable(
            retrofit.postComment(eventId,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let {model->
                            commentView.commentSuccess()
                        }
                    }, {
                        it.printStackTrace()
                    }
                )
        )
    }
}