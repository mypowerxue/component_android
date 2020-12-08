package com.xxx.common.model.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * 背景图
 */
public class BackTransformation extends SimpleTarget<Bitmap> {

    private View view;
    private Context context;

    BackTransformation(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), (android.graphics.Bitmap) resource);
        view.setBackground(circularBitmapDrawable);
    }
}
