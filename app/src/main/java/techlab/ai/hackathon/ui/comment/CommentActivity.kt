package techlab.ai.hackathon.ui.comment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import org.w3c.dom.Comment
import techlab.ai.hackathon.R
import techlab.ai.hackathon.common.toast.AppToast
import techlab.ai.hackathon.common.toast.ToastStyle
import techlab.ai.hackathon.data.model.CommentModel
import techlab.ai.hackathon.databinding.ActivityCommentBinding
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.profile.ProfileActivity

class CommentActivity : BaseActivity(),CommentView {

    companion object {
        @JvmStatic
        fun startSelf(context: Context, eventId : Long) {
            val starter = Intent(context, CommentActivity::class.java)
            starter.putExtra("eventId",eventId)
            context.startActivity(starter)
        }
    }
    private lateinit var commentController: CommentController
    private lateinit var binding : ActivityCommentBinding
    private lateinit var commentAdapter: CommentAdapter
    private var eventId : Long = -1


    override fun initBindingView(): View {
        binding = ActivityCommentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up )
        super.onCreate(savedInstanceState)
    }

    override fun afterCreatedView() {
        commentController = CommentController(this)
        commentAdapter = CommentAdapter()
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@CommentActivity)
            adapter = commentAdapter
        }

        binding.container.setOnClickListener {
            finish()
        }

        binding.textBoxView.setBtnSendOnClick{
            val text = binding.textBoxView.getEdtCmt().text.trim().toString()
            if (text.isBlank()){
                AppToast.createToast(ToastStyle.WARNING).setText("Vui lòng nhập nội dung").show(this)
            }else{
                commentController.postComment(eventId,binding.textBoxView.getEdtCmt().text.toString())
            }
        }

        eventId = intent?.getLongExtra("eventId",-1)?:-1
        if (eventId != -1L){
            refresh()
        }else{
            finish()
        }


    }

    override fun onCommentResult(comments: List<CommentModel>) {
        commentAdapter.listData= comments
    }

    override fun onFail() {

    }

    override fun commentSuccess() {
        binding.textBoxView.getEdtCmt().setText("")
        refresh()
    }

    private fun refresh(){
        commentController.getComment(eventId)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition( R.anim.slide_out_bottom, R.anim.slide_out_bottom )
    }
    override fun onDestroy() {
        super.onDestroy()
        commentController.clear()
    }
}