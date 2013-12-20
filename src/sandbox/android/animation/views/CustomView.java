package sandbox.android.animation.views;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {

    private Paint paint;
    public Circle circle;

    public CustomView(final Context context) {
        super(context);
        init();
    }

    public CustomView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

        circle = new Circle(new PointF(0,0), 100, Color.RED);
        circle.start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(circle.center.x,circle.center.y, circle.radius,paint);

        invalidate();
    }
}
