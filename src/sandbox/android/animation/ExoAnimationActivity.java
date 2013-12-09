package sandbox.android.animation;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ExoAnimationActivity extends Activity {

    Button btnStart;
    EXOImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final EXOAnimationQueue queue = new EXOAnimationQueue();

        imgLogo = (EXOImageView) findViewById(R.id.imgLogo);
        btnStart = (Button) findViewById(R.id.btnStart);

        EXOSpline spline = new EXOSpline();
        ArrayList<PointF> points = new ArrayList<PointF>(100);
        points.add(new PointF(0, 0));
        for (int i = 0; i < 100; ++i) {
            PointF point = new PointF();
            point.x = (float) (Math.random() * 240.f);
            point.y = (float) (Math.random() * 400.f);
            points.add(point);
        }
        points.add(new PointF(0, 0));
        spline.setValuesWithDuration(points, 10.0);
        EXOAnimation.spline = spline;

        EXOAnimationState state = EXOAnimationState.get(0, 0, 0, 1, 1);


        EXOAnimation anim1 = new EXOAnimation();
        anim1.generateTempAnimation(state, imgLogo);
        queue.add(anim1);
        queue.looping = false;


        // button click event
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start the animation
                queue.startAnimation(imgLogo);
            }
        });
    }
}
