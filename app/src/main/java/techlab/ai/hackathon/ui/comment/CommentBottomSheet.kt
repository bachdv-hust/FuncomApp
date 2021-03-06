package techlab.ai.hackathon.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import techlab.ai.hackathon.databinding.BottomSheetCommentBinding
import techlab.ai.hackathon.ui.base.BaseDialogFragment

class CommentBottomSheet : BaseDialogFragment() {

    private lateinit var binding: BottomSheetCommentBinding

    override fun initBindingView(): View {
        binding = BottomSheetCommentBinding.inflate(LayoutInflater.from(requireContext()),null,false)
        return binding.root
    }

    override fun afterCreatedView(view: View) {
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CommentAdapter()
        }
        binding.container.setOnClickListener {
            dismiss()
        }
    }


    companion object {

        private fun newInstance(): CommentBottomSheet {
            val args = Bundle()
            val fragment: CommentBottomSheet = CommentBottomSheet()
            fragment.arguments = args
            return fragment
        }

        fun show(fragmentManager: FragmentManager) {
            val bottomSheet: CommentBottomSheet = newInstance()
            bottomSheet.show(fragmentManager, "CommentBottomSheet")
        }
    }
}