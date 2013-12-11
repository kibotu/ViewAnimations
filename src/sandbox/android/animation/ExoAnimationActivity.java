package sandbox.android.animation;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ExoAnimationActivity extends Activity {

    Button btnStart;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

    public void runAnimation(EXOImageView view, double fps, EXOAnimationCollection collection) {
        final EXOAnimationQueue queue = new EXOAnimationQueue();
        queue.generateWithCollection(collection, view, fps);
        queue.looping = true;
        queue.run();
    }

    /**
     * @see <a href="https://redmine.exozet.com/issues/37448">Export Intro Screen</a>
     */
    public void createIntroScreenScene() {
        EXOAnimationScreenConfig screen = EXOAnimationScreenConfig.x480;
        EXOAnimationCollection.screen = screen;

        EXOAnimationCollection collection = new EXOAnimationCollection();

        addImage(355, 576, R.drawable.bg, screen);               // bg 355*576
        EXOImageView critter1 = addImage(67, 1080, R.drawable.critter_1, screen);        // critter_1 067*1080
        EXOImageView critter2 = addImage(435, 1067, R.drawable.critter_2, screen);       // critter_2 435*1067
        EXOImageView critter3 = addImage(845, 1135, R.drawable.critter_3, screen);       // critter_3 845*1135
        EXOImageView critter4 = addImage(209, 894, R.drawable.critter_4, screen);        // critter_4 209*894
        EXOImageView ray    = addImage(314, 242, R.drawable.ray, screen);              // ray 314*242
        EXOImageView blue   = addImage(355, 576, R.drawable.jelly_blue, screen);       // jelly_blue 355*576
        EXOImageView green  = addImage(355+100, 576, R.drawable.jelly_green, screen);      // jelly_green 355*576
        EXOImageView pink   = addImage(355+200, 576, R.drawable.jelly_pink, screen);       // jelly_pink 355*576
        EXOImageView red    = addImage(355+300, 576, R.drawable.jelly_red, screen);        // jelly_red 355*576
        EXOImageView yellow = addImage(355+400, 576, R.drawable.jelly_yellow, screen);     // jelly_yellow 355*576
        EXOImageView logo   = addImage(294, 268, R.drawable.logo, screen);             // logo 294*268
        EXOImageView kakaoText = addImage(239, 530, R.drawable.speech_ballon, screen);    // speech_ballon 239*530
        addImage(51, 564, R.drawable.tree_ol_1, screen);         // tree_ol_1 051*564
        addImage(909, 111, R.drawable.tree_ol_2, screen);        // tree_ol_2 909*111
        addImage(728, 0, R.drawable.tree_ol_3, screen);          // tree_ol_3 728*000

        ArrayList<PointF> points = new ArrayList<PointF>(100);
        points.add(new PointF(0, 0));
        for (int i = 0; i < 100; ++i) {
            PointF point = new PointF();
            point.x = (float) (Math.random() * 240.f);
            point.y = (float) (Math.random() * 400.f);
            points.add(point);
        }

        EXOAnimationCollection wiggle = EXOAnimationCollection.create()
                .addAnimation(EXOAnimationElementRepeat.create(15, EXOAnimationElementWiggle.create(0, 0.75, 15.0)))
                .addAnimation(EXOAnimationElementRepeat.create(10, EXOAnimationElementJump.create(0, 0.5, 70.0).appendAnimation(EXOAnimationElementJump.create(0, 0.5, 270.0))))
                .addAnimation(EXOAnimationElementRepeat.create(10, EXOAnimationElementWobble.create(0, 1, 0.3).applyCurve(EXOAnimationCurveCosineInOut.create(0.5, 0.5))));

        EXOAnimationElement elem = EXOAnimationElementJump.create(0, 0.5, 5.0).appendAnimation(EXOAnimationElementJump.create(0, 0.5, 1370.0));
        EXOAnimationElement zwo = EXOAnimationElementRepeat.create(10.0,elem);

        double fps = 4.0;
        double wave = 0.3;

        runAnimation(blue, fps      ,wiggle.waitBefore(wave*0.0));
        runAnimation(green, fps     ,wiggle.waitBefore(wave*1.0));
        runAnimation(pink, fps      ,wiggle.waitBefore(wave*2.0));
        runAnimation(red, fps       ,wiggle.waitBefore(wave*3.0));
        runAnimation(yellow, fps    ,wiggle.waitBefore(wave*4.0));

        runAnimation(critter1, fps  ,wiggle.waitBefore(wave*0.0));
        runAnimation(critter2, fps  ,wiggle.waitBefore(wave*1.0));
        runAnimation(critter3, fps  ,wiggle.waitBefore(wave*2.0));
        runAnimation(critter4, fps  ,wiggle.waitBefore(wave*3.0));
 
        runAnimation(logo, 10.0, EXOAnimationCollection.create().addAnimation(EXOAnimationElementRepeat.create(10.0, EXOAnimationElementWiggle.create(0, 3, 2.5))));
        runAnimation(ray, fps,EXOAnimationCollection.create().addAnimation(EXOAnimationElementRotate.create(0, 80, 16)));
        runAnimation(kakaoText, fps,EXOAnimationCollection.create().addAnimation(EXOAnimationElementRepeat.create(10.0, EXOAnimationElementBlink.create(0, 1))));
    }

    public EXOImageView addImage(final int x, final int y, final int resourceId, final EXOAnimationScreenConfig screen) {
        EXOImageView img = new EXOImageView(this);
        img.setImageResource(resourceId);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.width = (int) screen.scaleX(img.getDrawable().getIntrinsicWidth());
        lp.height = (int) screen.scaleY(img.getDrawable().getIntrinsicHeight());
        lp.setMargins((int) screen.scaleX(x) - lp.width / 2, (int) screen.scaleY(y) - lp.height / 2, 0, 0);

        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        img.setAdjustViewBounds(true);
        img.setLayoutParams(lp);
        layout.addView(img);
        return img;
    }

}
