package techlab.ai.hackathon.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @author BachDV
 */
abstract class BaseActivity : AppCompatActivity() {
    private var dialog: LoadingDialog?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    protected open fun showDialog() {
        try {
            getDialog()?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}