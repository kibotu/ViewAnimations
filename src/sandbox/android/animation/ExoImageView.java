package sandbox.android.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: jan.rabe
 * Date: 09.12.13
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class EXOImageView extends ImageView {
    Transformation additionalTransform;

    public EXOImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public EXOImageView(final Context context, final AttributeSet attrs) {

        super(context, attrs);
    }

    EXOImageView(final Context context) {
        super(context);
    }

    void applyTransform(Transformation transform) {
        additionalTransform = transform;
        /*
        if (transform != null)
        {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)(this.getLayoutParams());
            params.width = 10000;
            params.height = 10000;
            this.setLayoutParams(params);

            this.setImageMatrix(transform.getMatrix());
            this.setScaleType(ScaleType.CENTER);

            invalidate();
        }
        */
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
