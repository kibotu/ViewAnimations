package net.kibotu.android.view.animations.exo;

import android.content.Context;
import android.widget.RelativeLayout;

public class EXOAnimationsCollection {

    public Context context;
    public RelativeLayout layout;

    public EXOAnimationsCollection(final Context context, final RelativeLayout layout) {
        this.context = context;
        this.layout = layout;
    }

    public void runAnimation(EXOImageView view, double fps, AnimationGenerator generator) {
        System.out.print("EXOAnimations: Running an animation for image:" + view.getName() + "\n");

        generator.timeScale = 0.5;
        fps *= 0.575;
        final EXOAnimationQueue queue = new EXOAnimationQueue();
        queue.generateWithCollection(generator, view, fps);
        queue.looping = true;
        queue.run();
    }

    public EXOImageView addImage(final double x, final double y, final int resourceId, final EXOAnimationScreenConfig screen) {
        System.out.print("EXOAnimations: Adding an image for the Resource:" + resourceId + "\n");
        EXOImageView img = new EXOImageView(this.context);
        img.setName("resId:" + resourceId);
        //img.setDrawingCacheEnabled(true);
        //img.setDrawingCacheQuality(EXOImageView.DRAWING_CACHE_QUALITY_LOW);
        img.setScaleType(EXOImageView.ScaleType.FIT_XY); //(correct aspect ratio later)
        img.setImageResource(resourceId);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.width = (int) screen.imageViewScaleX(img.getDrawable().getIntrinsicWidth());
        lp.height = (int) screen.imageViewScaleY(img.getDrawable().getIntrinsicHeight());
        double posX = screen.imageViewPositionX(x);
        double posY = screen.imageViewPositionY(y);
        lp.setMargins((int) posX - lp.width / 2, (int) posY - lp.height / 2, -lp.width / 2, -lp.height / 2);
        img.setAdjustViewBounds(true);
        img.setLayoutParams(lp);
        layout.addView(img);
        return img;
    }

}
