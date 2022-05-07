package techlab.ai.hackathon.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<VB : ViewBinding>(context: Context) : Dialog(context) {
    protected lateinit var binding: VB

    protected abstract fun getViewBinding(): VB

    init {
        initView()
    }

    private fun initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = getViewBinding()
        setContentView(binding.root)

        window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            val lp: WindowManager.LayoutParams = attributes
            lp.dimAmount = 0.7f
            attributes = lp
        }
        setCancelable(true)
        setCanceledOnTouchOutside(true)

        configView()
    }

    abstract fun configView()

}