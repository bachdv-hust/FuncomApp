package techlab.ai.hackathon.ui.downloadapp

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import techlab.ai.hackathon.R
import techlab.ai.hackathon.ui.multi_choice.MultichoiceController
import techlab.ai.hackathon.ui.multi_choice.MultichoiceView
import techlab.ai.hackathon.ui.multi_choice.ResultQuestionDialog
import techlab.ai.hackathon.ui.multi_choice.ResultQuestionDialogSuccess


class DialogDownloadApp : DialogFragment(), MultichoiceView {
    private lateinit var btn_download : TextView
    private var packageNme : String = ""
    private var isDownload : Boolean = false
    private var eventId : Int = 0
    private var score : Int = 0
    private var multichoiceController : MultichoiceController? = null
    companion object {
        fun newInstance(packageName: String,eventId: Int,score: Int): DialogDownloadApp? {
            val frag = DialogDownloadApp()
            val args = Bundle()
            args.putString("packageName", packageName)
            args.putInt("eventId", eventId)
            args.putInt("score", score)
            frag.setArguments(args)
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            packageNme = getString("packageName") as String
            eventId = getInt("eventId") as Int
            score = getInt("score") as Int
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_download_app, container)
        if (getDialog() != null && getDialog()?.getWindow() != null) {
            getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            getDialog()?.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE);
        }
        initView(view)
        return view
    }

    private fun initView(view: View) {
        multichoiceController = MultichoiceController(this)
        btn_download = view.findViewById(R.id.btn_download_app)
        btn_download.setOnClickListener {
            openStore(packageNme)
        }
    }

    fun openStore(packageName : String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkIsDownload(packageNme)) {
            multichoiceController?.joinEvent(eventId.toLong())
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun checkIsDownload(uri : String) : Boolean {
        val packages: List<ApplicationInfo> =
          requireContext(). packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

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

    override fun joinEventSuccess(message: String) {
        dismiss()
        ResultQuestionDialogSuccess().newInstance(score.toDouble())?.show(parentFragmentManager,"ResultQuestionDialogSuccess")
    }

    override fun joinEventFail(message: String) {

    }
}