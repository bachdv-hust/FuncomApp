package techlab.ai.hackathon.ui.event_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import techlab.ai.hackathon.R
import techlab.ai.hackathon.common.load
import techlab.ai.hackathon.common.loadHtml
import techlab.ai.hackathon.common.openWebUrl
import techlab.ai.hackathon.data.model.EventDetail
import techlab.ai.hackathon.data.model.EventType
import techlab.ai.hackathon.databinding.ActivityEventDetailBinding
import techlab.ai.hackathon.databinding.ItemDonorsBinding
import techlab.ai.hackathon.databinding.ItemLinksBinding
import techlab.ai.hackathon.databinding.ItemUserJoinedBinding
import techlab.ai.hackathon.ui.comment.CommentActivity
import techlab.ai.hackathon.ui.persionjoined.PersonJoinedActivity

class EventDetailActivity : AppCompatActivity(), EventDetailView {

    companion object {
        fun startActivity(context: Context, eventId: String, userId: String) {
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtra("eventId", eventId)
            intent.putExtra("userId", userId)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityEventDetailBinding
    private val eventController by lazy {
        EventDetailController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.btnBack.setOnClickListener {
            finish()
        }

        val eventId = intent.getStringExtra("eventId")
        val userId = intent.getStringExtra("userId")
        binding.btnShare.setOnClickListener {
            CommentActivity.startSelf(this, eventId?.toLong() ?: -1)
        }
        binding.contentBody.btnSeeMore.setOnClickListener {
            PersonJoinedActivity.startActivity(this,eventId!!)
        }
        eventId?.let { userId?.let { it1 -> eventController.getEventDetail(it, it1) } }
    }

    override fun onEventDetailResult(eventDetail: EventDetail) {
        binding.ivEventCover.load(url = eventDetail.thumbnailUrl)
        binding.tvTitleToolbar.text = eventDetail.title
        binding.contentBody.tvDateTime.text = "${eventDetail.startDate} to ${eventDetail.endDate}"
        binding.contentBody.tvEventTitle.text = eventDetail.title
        binding.contentBody.tvCreateBy.text = "Tạo bởi ${eventDetail.createdBy?.username ?: ""}"
        val receiveFunCoin = eventDetail.receiveFunCoin ?: 0
        val totalFunCoin = eventDetail.totalFunCoin ?: 0
        if (totalFunCoin >= 0) {
            binding.contentBody.progressCoin.setProgress(0)
        } else {
            val percentRemain = (receiveFunCoin / totalFunCoin) * 100
            binding.contentBody.progressCoin.setProgress(receiveFunCoin)
            binding.contentBody.progressCoin.setMaxProgress(totalFunCoin)
        }

        binding.contentBody.tvCoinCirculating.text = receiveFunCoin.toString()
        binding.contentBody.tvCoinTotal.text = totalFunCoin.toString()
        binding.contentBody.tvEventDescription.loadHtml(eventDetail.description.toString())
        if (eventDetail.type == EventType.DOWNLOAD.ordinal) {
            binding.contentBody.ctnDownload.visibility = View.VISIBLE
            binding.contentBody.ctnSurvey.visibility = View.GONE
        } else if (eventDetail.type == EventType.SURVEY.ordinal) {
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
            for (user in eventDetail.userJoined) {
                val viewUsers =
                    LayoutInflater.from(this)
                        .inflate(R.layout.item_user_joined, binding.contentBody.llUsersJoin, false)
                val vUsers = ItemUserJoinedBinding.bind(viewUsers)
                vUsers.ivUserAvatar.load(url = user.user?.avatar)
                vUsers.tvUserName.text = user.user?.nameDisplay()
                vUsers.tvCoin.text = "+${user.coin}"
                binding.contentBody.llUsersJoin.addView(viewUsers)
            }
        } else {
            binding.contentBody.llUsersJoin.visibility = View.GONE
            binding.contentBody.rlHeaderUserJoin.visibility = View.GONE
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
    }
}