package com.xxx.common.model.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * 圆形
 */
public class CircleTransformation extends BitmapImageViewTarget {

    private ImageView imageView;
    private Context context;

    CircleTransformation(Context context, ImageView imageView) {
        super(imageView);
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected void setResource(Bitmap resource) {
        super.setResource(resource);
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
        circularBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(circularBitmapDrawable);
    }
}
