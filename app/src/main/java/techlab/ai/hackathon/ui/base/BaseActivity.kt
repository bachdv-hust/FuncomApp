package techlab.ai.hackathon.ui.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @author BachDV
 */
abstract class BaseActivity : AppCompatActivity() {
    private var dialog: LoadingDialog?= null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(initBindingView())
        afterCreatedView()
    }

    abstract fun initBindingView() : View
    abstract fun afterCreatedView()

    protected open fun getDialog(): LoadingDialog? {
        if (dialog == null) {
            dialog = LoadingDialog.instance(this)
            dialog?.setCancelable(true)
        }
        return dialog
    }

    protected open fun hideDialog() {
        dialog?.hide()
        dialog = null
    }

    protected open fun showDialog() {
        try {
            getDialog()?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}