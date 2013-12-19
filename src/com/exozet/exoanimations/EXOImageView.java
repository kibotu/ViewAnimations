package com.exozet.exoanimations;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class EXOImageView extends ImageView {

    double anchorX;
    public double anchorY;
    String name;

    public EXOImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EXOImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EXOImageView(final Context context) {
        super(context);
        init();
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void init() {
        anchorX = 0.5;
        anchorY = 0.5;
        name = "";
    }
}
