package techlab.ai.hackathon.ui.event_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentManager
import techlab.ai.hackathon.common.pushdown.PushDownAnim
import techlab.ai.hackathon.databinding.DialogDownLoadAppBinding
import techlab.ai.hackathon.ui.base.BaseDialogFragment

class DownLoadAppDialog : BaseDialogFragment() {

    companion object {

        private fun newInstance(): DownLoadAppDialog {
            val args = Bundle()
            val fragment = DownLoadAppDialog()
            fragment.arguments = args
            return fragment
        }

        fun show(fragmentManager: FragmentManager):DownLoadAppDialog {
            val bottomSheet: DownLoadAppDialog = newInstance()
            bottomSheet.show(fragmentManager, "DownLoadAppDialog")
            return bottomSheet
        }
    }
     var callBack :DownloadDialogCallBack?= null
    private lateinit var binding: DialogDownLoadAppBinding
    override fun initBindingView(): View {
        binding =
            DialogDownLoadAppBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        return binding.root
    }

    override fun afterCreatedView(view: View) {
//        PushDownAnim.setPushDownAnimTo(binding.btnDownload).setOnClickListener {
//            dismiss()
//            callBack?.oncClickDownLoadBtn()
//        }
    }


}
interface DownloadDialogCallBack{
    fun oncClickDownLoadBtn()
}