package techlab.ai.hackathon.ui.event_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.AppBarLayout
import techlab.ai.hackathon.R
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.common.loadHtml
import techlab.ai.hackathon.common.openWebUrl
import techlab.ai.hackathon.common.pushdown.PushDownAnim
import techlab.ai.hackathon.common.toast.AppToast
import techlab.ai.hackathon.common.toast.ToastStyle
import techlab.ai.hackathon.data.model.EventDetail
import techlab.ai.hackathon.databinding.*
import techlab.ai.hackathon.share_ui.avatagen.AvatarGenerator
import techlab.ai.hackathon.ui.base.BaseActivity
import techlab.ai.hackathon.ui.comment.CommentActivity
import techlab.ai.hackathon.ui.downloadapp.DialogDownloadApp
import techlab.ai.hackathon.ui.login.LoginDialog
import techlab.ai.hackathon.ui.multi_choice.MultiChoiceActivity
import techlab.ai.hackathon.ui.multi_choice.MultichoiceController
import techlab.ai.hackathon.ui.multi_choice.MultichoiceView
import techlab.ai.hackathon.ui.multi_choice.ResultQuestionDialogSuccess
import techlab.ai.hackathon.ui.persionjoined.PersonJoinedActivity
import techlab.ai.hackathon.ui.view_descript.ViewDescriptionActivity
import kotlin.math.abs

class EventDetailActivity : BaseActivity(), EventDetailView ,MultichoiceView{

    companion object {
        fun startActivity(context: Context, eventId: String, userId: String) {
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtra("eventId", eventId)
            intent.putExtra("userId", userId)
            context.startActivity(intent)
        }
    }

    private var isDownloadApp : Boolean = false

    private lateinit var binding: ActivityEventDetailBinding
    private var eventDetail : EventDetail? = null
    private lateinit var multichoiceController: MultichoiceController
    private val eventController by lazy {
        EventDetailController(this)
    }


    override fun initBindingView(): View {
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun afterCreatedView() {
        setSupportActionBar(findViewById(R.id.toolbar))
        multichoiceController = MultichoiceController(this)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            // Apply the insets as padding to the view. Here we're setting all of the
            // dimensions, but apply as appropriate to your layout. You could also
            // update the views margin if more appropriate.
            Log.d("insets.top=", insets.top.toString())
            val params = binding.toolbar.layoutParams as FrameLayout.LayoutParams
            params.setMargins(0, insets.top, 0, 0)
            binding.toolbar.layoutParams = params
            // Return CONSUMED if we don't want the window insets to keep being passed
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
        binding.toolbarLayout.title = title
        binding.btnBack.setOnClickListener {
            finish()
        }

        val eventId = intent.getStringExtra("eventId")
        val userId = intent.getStringExtra("userId")
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                //  Collapsed
                binding.tvTitleToolbar.visibility = View.VISIBLE
            } else {
                //Expanded
                binding.tvTitleToolbar.visibility = View.GONE
            }
        })
        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Chia sẻ ngay cho bạn bè để kiếm FunCoin miễn phí..")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        binding.btnComment.setOnClickListener {
            if (validateLogin()) {
                CommentActivity.startSelf(this, eventId?.toLong() ?: -1)
            }
        }
        PushDownAnim.setPushDownAnimTo(binding.contentBody.ctnDownload).setOnClickListener {
            val dialog = DownLoadAppDialog.show(supportFragmentManager)
            dialog.callBack = object : DownloadDialogCallBack {
                override fun oncClickDownLoadBtn() {
                    // TODO
                }
            }
        }
        eventId?.let { userId?.let { it1 -> eventController.getEventDetail(it, it1) } }
    }

    private fun validateLogin(): Boolean {
        return if (SharePref.isLogin) {
            true
        } else {
            LoginDialog.show(supportFragmentManager)
            false
        }
    }

    fun initEvent(eventDetail: EventDetail) {
        binding.btnJoin.setOnClickListener {
            if(!validateLogin()){
                return@setOnClickListener
            }
            if (SharePref.getEventCached(eventDetail.id!!)) {
                //dang doi xac nhan, xu ly check xem join hay chua
                SharePref.setEventCached(eventDetail.id!!, false)
                checkStateJoin(eventDetail)
            } else {
                when(eventDetail.type) {
                    1 -> {
                        val intent = Intent(this, MultiChoiceActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("data", eventDetail)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                    2 -> {
                        DialogDownloadApp.newInstance("com.ffff.docbao24h",eventDetail.id ?: 0,eventDetail.receiveFunCoin?:0)?.show(supportFragmentManager,"DialogDownloadApp")
                        SharePref.setEventCached(eventDetail.id!!, true)
                        checkStateJoin(eventDetail)
                        isDownloadApp = true
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (isDownloadApp) {
            if (checkIsDownload("com.ffff.docbao24h")) {
                eventDetail?.id?.let { it.toLong()
                    .let { it1 -> multichoiceController.joinEvent(it1) } }
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun checkIsDownload(uri : String) : Boolean {
        val pm: PackageManager? = getPackageManager()
        try {
            pm?.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }

    override fun onEventDetailResult(eventDetail: EventDetail) {
        try {
            this.eventDetail = eventDetail
            initEvent(eventDetail)
            binding.ivEventCover.load(url = eventDetail.thumbnailUrl)
            binding.tvTitleToolbar.text = eventDetail.title
            binding.contentBody.tvDateTime.text =
                "${eventDetail.startDate} to ${eventDetail.endDate}"
            binding.contentBody.tvEventTitle.text = eventDetail.title
            eventDetail.createdBy?.let {
                binding.contentBody.tvCreateBy.text = "Tạo bởi ${it.username}"
            } ?: kotlin.run {
                binding.contentBody.tvCreateBy.visibility = View.GONE
            }

            val receiveFunCoin = eventDetail.receiveFunCoin ?: 0
            val totalFunCoin = eventDetail.totalFunCoin ?: 0
            if (totalFunCoin <= 0) {
                binding.contentBody.progressCoin.setProgress(0)
            } else {
                val percentRemain = (receiveFunCoin / totalFunCoin) * 100
                binding.contentBody.progressCoin.setMaxProgress(totalFunCoin)
                binding.contentBody.progressCoin.setProgress(receiveFunCoin)
            }
            binding.contentBody.btnSeeMore.setOnClickListener {
                ViewDescriptionActivity.startActivity(this, eventDetail.description!!)
            }
            binding.contentBody.tvEventDescription.setOnClickListener {
                ViewDescriptionActivity.startActivity(this, eventDetail.description!!)
            }
            binding.contentBody.headerUserJoin.setOnClickListener {
                PersonJoinedActivity.startActivity(this, eventDetail)
            }
            binding.contentBody.tvViewMoreUserJoin.setOnClickListener {
                PersonJoinedActivity.startActivity(this, eventDetail)
            }
            binding.contentBody.tvCoinCirculating.text = receiveFunCoin.toString()
            binding.contentBody.tvCoinTotal.text = totalFunCoin.toString()
            binding.contentBody.tvEventDescription.loadHtml(eventDetail.description.toString())
            if (eventDetail.type == 2) {
                binding.contentBody.ctnDownload.visibility = View.VISIBLE
                binding.contentBody.ctnSurvey.visibility = View.GONE
            } else if (eventDetail.type == 1) {
                binding.contentBody.ctnDownload.visibility = View.GONE
                binding.contentBody.ctnSurvey.visibility = View.VISIBLE
            } else {
                binding.contentBody.headerMission.visibility = View.GONE
                binding.contentBody.ctnDownload.visibility = View.GONE
                binding.contentBody.ctnSurvey.visibility = View.GONE
            }
            //nha tai tro
            if (!eventDetail.donors.isNullOrEmpty()) {
                for (donor in eventDetail.donors) {
                    val viewDonors =
                        LayoutInflater.from(this)
                            .inflate(R.layout.item_donors, binding.contentBody.llDonors, false)
                    val vDonor = ItemDonorsBinding.bind(viewDonors)
                    vDonor.ivUserAvatar.load(url = donor.icon)
                    vDonor.tvDonors.text = donor.name
                    binding.contentBody.llDonors.addView(viewDonors)
                }

            } else {
                binding.contentBody.llDonors.visibility = View.GONE
                binding.contentBody.headerDonors.visibility = View.GONE
            }
            //da tham gia
            if (!eventDetail.userJoined.isNullOrEmpty()) {
                binding.contentBody.headerUserJoin.setText("${eventDetail.userJoined.size} người đã tham gia")
                var count = 0;
                for (user in eventDetail.userJoined) {
                    if (count >= 3) break
                    val viewUsers =
                        LayoutInflater.from(this)
                            .inflate(
                                R.layout.item_user_joined,
                                binding.contentBody.llUsersJoin,
                                false
                            )
                    val vUsers = ItemUserJoinedBinding.bind(viewUsers)
                    val av = AvatarGenerator.AvatarBuilder(this)
                        .setLabel(user.user?.lastName ?: "")
                        .setAvatarSize(120)
                        .setTextSize(30)
                        .toSquare()
                        .toCircle()
                        .build()
                    vUsers.ivUserAvatar.setImageDrawable(
                        av
                    )
                    vUsers.tvUserName.text = user.user?.nameDisplay()
                    vUsers.tvCoin.text = "+${user.coin}"
                    binding.contentBody.llUsersJoin.addView(viewUsers)
                    count++
                }
            } else {
                binding.contentBody.llUsersJoin.visibility = View.GONE
                binding.contentBody.rlHeaderUserJoin.visibility = View.GONE
            }
            //highlight
            if (!eventDetail.highlights.isNullOrEmpty()) {
                for (highlight in eventDetail.highlights) {
                    val viewHightL =
                        LayoutInflater.from(this)
                            .inflate(R.layout.item_highligh, binding.contentBody.llHighlight, false)
                    val vHL = ItemHighlighBinding.bind(viewHightL)
                    vHL.tvText.text = highlight.title
                    binding.contentBody.llHighlight.addView(viewHightL)
                }
            } else {
                binding.contentBody.llHighlight.visibility = View.GONE
                binding.contentBody.headerHighlight.visibility = View.GONE
            }
            //lien ket
            if (!eventDetail.links.isNullOrEmpty()) {
                for (link in eventDetail.links) {
                    val viewLinks =
                        LayoutInflater.from(this)
                            .inflate(R.layout.item_links, binding.contentBody.llLinks, false)
                    val vLinks = ItemLinksBinding.bind(viewLinks)
                    viewLinks.setOnClickListener {
                        link.link?.openWebUrl(this)
                    }
                    vLinks.tvLink.text = link.name
                    binding.contentBody.llLinks.addView(viewLinks)
                }
            } else {
                binding.contentBody.llLinks.visibility = View.GONE
                binding.contentBody.headerLinks.visibility = View.GONE
            }
            checkStateJoin(eventDetail)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        hideDialog()
    }

    override fun loadEventFail() {
        hideDialog()
        AppToast.createToast(ToastStyle.ERROR).setText("Có lỗi xảy ra!").show(this)
    }

    private fun checkStateJoin(eventDetail: EventDetail) {
        if (SharePref.getEventCached(eventDetail.id!!)) {
            binding.btnJoin.setBackgroundResource(R.drawable.bg_btn_register_enable)
            binding.btnJoin.text = "Xác nhận đã tham gia"
        } else {
            if (eventDetail.isUserJoined!!) {
                binding.btnJoin.setBackgroundResource(R.drawable.bg_btn_register_disable)
                binding.btnJoin.text = "Đã tham gia"
            } else {
                binding.btnJoin.setBackgroundResource(R.drawable.bg_btn_join_enable)
                binding.btnJoin.text = "Tham gia ngay"
            }
        }


    }

    override fun joinEventSuccess(message: String) {
        eventDetail?.receiveFunCoin?.let { it.toDouble()
            .let { it1 -> ResultQuestionDialogSuccess().newInstance(it1)?.show(supportFragmentManager,"ResultQuestionDialogSuccess") } }
    }

    override fun joinEventFail(message: String) {

    }
}