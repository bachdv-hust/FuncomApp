package techlab.ai.hackathon.ui.comment

import techlab.ai.hackathon.data.model.CommentModel

/**
 * @author BachDV
 */
interface CommentView {
    fun onCommentResult(comments : List<CommentModel>)
    fun onFail()
    fun commentSuccess()
}