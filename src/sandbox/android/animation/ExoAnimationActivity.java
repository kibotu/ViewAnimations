package sandbox.android.animation;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ExoAnimationActivity extends Activity {

    Button btnStart;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoanim);
        layout = (RelativeLayout) findViewById(R.id.mainLayout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        createIntroScreenScene();
    }

    /**
     * @see <a href="https://redmine.exozet.com/projects/lines/wiki/Screen_support_and_definitions">Screen_support_and_definitions</a>
     */
    enum Scale {

        x320(0.2083f, 0.208333f),
        x480(0.3125f, 0.3125f),
        x540(0.3515625f, 0.35156f),
        x600(0.390625f, 0.390625f),
        x720(0.46875f, 0.46875f),
        x768(0.5f, 0.50f),
        x800(0.52083f, 0.520833f),
        x1080(0.703125f, 0.703125f),
        NO_SCALING(1, 1);

        private final float scaleX, scaleY;

        private Scale(final float scaleX, final float scaleY) {
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }

        public float scaleX(final float x) {
            return x * scaleX / x768.scaleX;
        }

        public float scaleY(final float y) {
            return y * scaleY / x768.scaleY;
        }
    }


    public void runAnimation(EXOImageView view, EXOAnimationCollection collection) {
        final EXOAnimationQueue queue = new EXOAnimationQueue();
        queue.fps = 1.0 / 2.0;
        queue.generateWithCollection(collection, view);
        queue.looping = true;
        queue.run();
    }

    /**
     * @see <a href="https://redmine.exozet.com/issues/37448">Export Intro Screen</a>
     */
    public void createIntroScreenScene() {

        Scale s = Scale.x480;

        EXOAnimationCollection collection = new EXOAnimationCollection();

        addImage(355, 576, R.drawable.bg, s);               // bg 355*576
        addImage(67, 1080, R.drawable.critter_1, s);        // critter_1 067*1080
        addImage(435, 1067, R.drawable.critter_2, s);       // critter_2 435*1067
        addImage(845, 1135, R.drawable.critter_3, s);       // critter_3 845*1135
        addImage(209, 894, R.drawable.critter_4, s);        // critter_4 209*894
        EXOImageView blue = addImage(355, 576, R.drawable.jelly_blue, s);       // jelly_blue 355*576
        EXOImageView green = addImage(355, 576, R.drawable.jelly_green, s);      // jelly_green 355*576
        EXOImageView pink = addImage(355, 576, R.drawable.jelly_pink, s);       // jelly_pink 355*576
        EXOImageView red = addImage(355, 576, R.drawable.jelly_red, s);        // jelly_red 355*576
        EXOImageView yellow = addImage(355, 576, R.drawable.jelly_yellow, s);     // jelly_yellow 355*576
        EXOImageView ray = addImage(314, 242, R.drawable.ray, s);              // ray 314*242
        addImage(294, 268, R.drawable.logo, s);             // logo 294*268
        addImage(239, 530, R.drawable.speech_ballon, s);    // speech_ballon 239*530
        addImage(51, 564, R.drawable.tree_ol_1, s);         // tree_ol_1 051*564
        addImage(909, 111, R.drawable.tree_ol_2, s);        // tree_ol_2 909*111
        addImage(728, 0, R.drawable.tree_ol_3, s);          // tree_ol_3 728*000

        ArrayList<PointF> points = new ArrayList<PointF>(100);
        points.add(new PointF(0, 0));
        for (int i = 0; i < 100; ++i) {
            PointF point = new PointF();
            point.x = (float) (Math.random() * 240.f);
            point.y = (float) (Math.random() * 400.f);
            points.add(point);
        }

        runAnimation(blue, EXOAnimationCollection.create().addAnimation(EXOAnimationElementRotate.create(0, 80, 16)).addAnimation(EXOAnimationElementSpline.create(0, 80, points)));
        runAnimation(ray, EXOAnimationCollection.create().addAnimation(EXOAnimationElementRotate.create(0, 80, 16)));
    }

    public EXOImageView addImage(final int x, final int y, final int resourceId, final Scale scale) {
        EXOImageView img = new EXOImageView(this);
        img.setImageResource(resourceId);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.width = (int) scale.scaleX(img.getDrawable().getIntrinsicWidth());
        lp.height = (int) scale.scaleY(img.getDrawable().getIntrinsicHeight());
        lp.setMargins((int) scale.scaleX(x) - lp.width / 2, (int) scale.scaleY(y) - lp.height / 2, 0, 0);

        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        img.setAdjustViewBounds(true);
        img.setLayoutParams(lp);
        layout.addView(img);
        return img;
    }

    public void testAnimStuff() {

        EXOImageView imgLogo = addImage(50, 150, R.drawable.mr_bean, Scale.NO_SCALING);
        EXOImageView imgLogo2 = addImage(200, 200, R.drawable.mr_bean, Scale.NO_SCALING);

        btnStart = (Button) findViewById(R.id.btnStart);

        EXOAnimationCollection collection = new EXOAnimationCollection();

        ArrayList<PointF> points = new ArrayList<PointF>(100);
        points.add(new PointF(0, 0));
        for (int i = 0; i < 100; ++i) {
            PointF point = new PointF();
            point.x = (float) (Math.random() * 240.f);
            point.y = (float) (Math.random() * 400.f);
            points.add(point);
        }

        collection.addElement(EXOAnimationElementSpline.create(0, 10, points));
        collection.addElement(EXOAnimationElementWobble.create(5, 10, 0.5, 5));
        collection.addElement(EXOAnimationElementWobble.create(0, 10, 0.2, 25));

        final EXOAnimationQueue queue = new EXOAnimationQueue();
        queue.generateWithCollection(collection, imgLogo);
        queue.looping = false;


        // button click event
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start the animation
                queue.run();
            }
        });
    }
}
