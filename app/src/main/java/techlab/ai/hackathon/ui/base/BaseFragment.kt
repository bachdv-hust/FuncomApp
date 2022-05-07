package techlab.ai.hackathon.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author BachDV
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBindingView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterCreatedView()
    }

    abstract fun initBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    abstract fun afterCreatedView()
    private var dialog: LoadingDialog?= null
    protected open fun getDialog(): LoadingDialog? {
        if (dialog == null) {
            dialog = LoadingDialog.instance(requireActivity())
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