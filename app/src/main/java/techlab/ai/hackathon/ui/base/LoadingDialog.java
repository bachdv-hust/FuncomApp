package techlab.ai.hackathon.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import techlab.ai.hackathon.R;

/**
 * @author BachDV
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        this(context, 0);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static LoadingDialog instance(Activity activity) {
        LinearLayout loadingView = (LinearLayout) View.inflate(activity, R.layout.common_progress_view, null);
//        v.setColor(ContextCompat.getColor(activity, R.color.menu_bg_color));
        LoadingDialog dialog = new LoadingDialog(activity, R.style.loading_dialog);
        dialog.setContentView(loadingView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
        );
        dialog.setCanceledOnTouchOutside(false);
        try {
            dialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public void stopLoadingView() {

    }

}