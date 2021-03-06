package techlab.ai.hackathon.ui.event_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.AppBarLayout
import techlab.ai.hackathon.R
import techlab.ai.hackathon.cached.SharePref
import techlab.ai.hackathon.common.coinFormat
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
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
    private var packageApp : String = ""

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
            Log.d("insets.top=", insets.top.toString())
            val params = binding.toolbar.layoutParams as FrameLayout.LayoutParams
            params.setMargins(0, insets.top, 0, 0)
            binding.toolbar.layoutParams = params
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
                putExtra(Intent.EXTRA_TEXT, "Chia s??? ngay cho b???n b?? ????? ki???m FunCoin mi???n ph??..")
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
//        PushDownAnim.setPushDownAnimTo(binding.contentBody.ctnDownload).setOnClickListener {
//            DialogDownloadApp.newInstance()
//        }
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

    private fun initEvent(eventDetail: EventDetail) {
        PushDownAnim.setPushDownAnimTo(binding.btnJoin).setOnClickListener {
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
                        startActivityForResult(intent,1000)
                    }
                    2 -> {
                        if (checkIsDownload(packageApp)) {
                            multichoiceController.joinEvent(eventDetail.id!!.toLong())
                        } else {
                            DialogDownloadApp.newInstance(packageApp,eventDetail.id ?: 0,eventDetail.receiveFunCoin?:0)?.show(supportFragmentManager,"DialogDownloadApp")
                        }
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
//        if (isDownloadApp) {
//            if (checkIsDownload(packageApp)) {
//                eventDetail?.id?.let { it.toLong()
//                    .let { it1 -> multichoiceController.joinEvent(it1) } }
//            }
//        }

        eventDetail?.let { checkStateJoin(it) }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun checkIsDownload(uri : String) : Boolean {
//        val pm: PackageManager? = getPackageManager()
//        try {
//            pm?.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
//            return true
//        } catch (e: PackageManager.NameNotFoundException) {
//        }
//        return false
        val packages: List<ApplicationInfo> =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        return try {
            packages.forEach {
                if (it.packageName.contains(uri)){
                    return true
                }
            }
            return false
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun checkEndtime(time: String) : Boolean{
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.parse(time)
        return date.before(Date())
    }

    override fun onEventDetailResult(eventDetail: EventDetail) {
        try {
            this.eventDetail = eventDetail
            eventDetail?.link_app?.let {
                it.forEachIndexed { index, links ->
                    if (links.platform.equals("Android")) {
                        packageApp = links.packageApp.toString()
                    }
                }
            }
            checkStateJoin(eventDetail)
            initEvent(eventDetail)
            binding.ivEventCover.load(url = eventDetail.thumbnailUrl)
            binding.tvTitleToolbar.text = eventDetail.title
            try {
                val dateFormat: Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eventDetail.startDate)
                val dateFormat2: Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(eventDetail.endDate)
                val strDate: String = SimpleDateFormat("HH:mm dd/MM").format(dateFormat)
                val endDate: String = SimpleDateFormat("HH:mm dd/MM/yyyy").format(dateFormat2)
                binding.contentBody.tvDateTime.text =
                    "${strDate} to ${endDate}"
            } catch (e: Exception) {
            }
            binding.contentBody.tvEventTitle.text = eventDetail.title
            eventDetail.createdBy?.let {
                binding.contentBody.tvCreateBy.text = "T???o b???i ${it.username}"
            } ?: kotlin.run {
                binding.contentBody.tvCreateBy.visibility = View.GONE
            }

            val receiveFunCoin = eventDetail.receiveFunCoin ?: 0
            val remainingFunCoin = eventDetail.remainingFunCoin ?: 0
            val totalFunCoin = eventDetail.totalFunCoin ?: 0
            if (totalFunCoin <= 0) {
                binding.contentBody.progressCoin.setProgress(0)
            } else {
//                val percentRemain = (receiveFunCoin / totalFunCoin) * 100
                binding.contentBody.progressCoin.setMaxProgress(totalFunCoin)
                binding.contentBody.progressCoin.setProgress(totalFunCoin- remainingFunCoin)
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
            binding.contentBody.tvCoinCirculating.text = (totalFunCoin- remainingFunCoin).coinFormat()
            binding.contentBody.tvCoinTotal.text = totalFunCoin.coinFormat()
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
                    PushDownAnim.setPushDownAnimTo(viewDonors).setOnClickListener {
                        donor.link?.openWebUrl(this)
                    }
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
                binding.contentBody.headerUserJoin.setText("${eventDetail.userJoined.size} ng?????i ???? tham gia")
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
                    PushDownAnim.setPushDownAnimTo(viewLinks).setOnClickListener {
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
        AppToast.createToast(ToastStyle.ERROR).setText("C?? l???i x???y ra!").show(this)
    }

    private fun checkStateJoin(eventDetail: EventDetail) {
        if (eventDetail.endDate?.let { checkEndtime(it) } == true ){
            // Het han
            if (eventDetail.isUserJoined == true){
                binding.btnJoin.isEnabled = false
                binding.btnJoin.setBackgroundResource(R.drawable.bg_btn_register_disable)
                binding.btnJoin.text = "???? tham gia"
            }else{
                binding.btnJoin.isEnabled = false
                binding.btnJoin.setBackgroundResource(R.drawable.bg_btn_register_disable)
                binding.btnJoin.text = "H???t h???n"
            }
        }else{
            // chua het han
            if (eventDetail.isUserJoined == true){
                binding.btnJoin.isEnabled = false
                binding.btnJoin.setBackgroundResource(R.drawable.bg_btn_register_disable)
                binding.btnJoin.text = "???? tham gia"
            }else{
                if (eventDetail.remainingFunCoin == 0) {
                    binding.btnJoin.isEnabled = false
                    binding.btnJoin.setBackgroundResource(R.drawable.bg_btn_register_disable)
                    binding.btnJoin.text = "S??? l?????ng coin ???? h???t"
                } else {
                    binding.btnJoin.isEnabled = true
                    binding.btnJoin.setBackgroundResource(R.drawable.bg_btn_join_enable)
                    binding.btnJoin.text = "Tham gia ngay"
                }
            }
        }

    }

    override fun joinEventSuccess(message: String) {
        eventDetail?.receiveFunCoin?.let { it.toDouble()
            .let { it1 -> ResultQuestionDialogSuccess().newInstance(it1)?.show(supportFragmentManager,"ResultQuestionDialogSuccess") } }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val coin = (eventDetail?.totalFunCoin?.minus(eventDetail?.remainingFunCoin?: 0))?.plus(
                eventDetail?.receiveFunCoin ?: 0
            )
            eventDetail?.isUserJoined = true
            binding.contentBody.tvCoinCirculating.text = coin.toString()
            eventDetail?.let { checkStateJoin(it) }
        }
    }

    override fun joinEventFail(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}