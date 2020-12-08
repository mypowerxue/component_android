package com.xxx.common.ui.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

public class EditTextShakeHelper {

    private Animation shakeAnimation;

    private CycleInterpolator cycleInterpolator;

    private Vibrator shakeVibrator;

    public EditTextShakeHelper(Context context) {
        shakeVibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        shakeAnimation = new TranslateAnimation(0, 10, 0, 0);
        shakeAnimation.setDuration(300);
        cycleInterpolator = new CycleInterpolator(8);
        shakeAnimation.setInterpolator(cycleInterpolator);
    }

    @SuppressLint("MissingPermission")
    public void shake(View... views) {
        for (View view : views) {
            view.startAnimation(shakeAnimation);
        }
        shakeVibrator.vibrate(new long[]{0, 500}, -1);
    }

}