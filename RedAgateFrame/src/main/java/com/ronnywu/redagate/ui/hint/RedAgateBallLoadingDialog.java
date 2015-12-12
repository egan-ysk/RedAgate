package com.ronnywu.redagate.ui.hint;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.ronnywu.redagate.R;


/**
 * 帧动画.
 */
public class RedAgateBallLoadingDialog extends Dialog {

    private AnimationDrawable animationDrawable;

    public RedAgateBallLoadingDialog(Context context) {
        super(context, R.style.RedAgate_Dialog);
        init();
    }

    private void init() {
        setContentView(R.layout.red_agate_ball_loading_dialog);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
    }

    @Override
    public void show() {
        super.show();
        animationDrawable.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animationDrawable.stop();
    }
}
