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

    public void runAnimation(EXOImageView view, double fps, EXOAnimationGenerator generator) {
        final EXOAnimationQueue queue = new EXOAnimationQueue();
        queue.generateWithCollection(generator, view, fps);
        queue.looping = true;
        queue.run();
    }

    /**
     * @see <a href="https://redmine.exozet.com/issues/37448">Export Intro Screen</a>
     */
    public void createIntroScreenScene() {
        EXOAnimationScreenConfig screen = EXOAnimationScreenConfig.x600;
        EXOAnimationGenerator.screen = screen;


        addImage(355, 576, R.drawable.bg, screen);               // bg 355*576
        EXOImageView ray    = addImage(314, 242, R.drawable.ray, screen);              // ray 314*242
        int particleCount = 7;
        for (int i = 0; i < particleCount; ++i)
        {
            EXOImageView particle = addImage(314,242,R.drawable.particle,screen);
            EXOAnimationGenerator generator = EXOAnimationGenerator.create();
            double angle = Math.random()*2.0*Math.PI;

            generator.addAnimation(EXOAnimationElementMove.create(0,1, 0,0,Math.sin(angle)*340.0,Math.cos(angle)*340.0).addAnimation(EXOAnimationElementFadeInOut.create(0,1)));
            runAnimation(particle,5.0,generator.waitBefore(Math.random()*2.0));
        }

        EXOImageView blue   = addImage(355, 576, R.drawable.jelly_blue, screen);       // jelly_blue 355*576
        EXOImageView green  = addImage(355+75, 576, R.drawable.jelly_green, screen);      // jelly_green 355*576
        EXOImageView pink   = addImage(355+150, 576, R.drawable.jelly_pink, screen);       // jelly_pink 355*576
        EXOImageView red    = addImage(355+225, 576, R.drawable.jelly_red, screen);        // jelly_red 355*576
        EXOImageView yellow = addImage(355+300, 576, R.drawable.jelly_yellow, screen);     // jelly_yellow 355*576
        EXOImageView logo   = addImage(294, 268, R.drawable.logo, screen);             // logo 294*268
        EXOImageView kakaoText = addImage(239, 530, R.drawable.speech_ballon, screen);    // speech_ballon 239*530
        EXOImageView tree1 = addImage(51, 564, R.drawable.tree_ol_1, screen);         // tree_ol_1 051*564
        EXOImageView tree2 = addImage(909, 111, R.drawable.tree_ol_2, screen);        // tree_ol_2 909*111
        EXOImageView tree3 = addImage(728, 0, R.drawable.tree_ol_3, screen);          // tree_ol_3 728*000
        EXOImageView critter1 = addImage(67, 1080, R.drawable.critter_1, screen);        // critter_1 067*1080
        EXOImageView critter2 = addImage(435, 1067, R.drawable.critter_2, screen);       // critter_2 435*1067
        EXOImageView critter3 = addImage(845, 1135, R.drawable.critter_3, screen);       // critter_3 845*1135
        EXOImageView critter4 = addImage(209, 894, R.drawable.critter_4, screen);        // critter_4 209*894

        ArrayList<PointF> points = new ArrayList<PointF>(100);
        points.add(new PointF(0, 0));
        for (int i = 0; i < 100; ++i) {
            PointF point = new PointF();
            point.x = (float) (Math.random() * 240.f);
            point.y = (float) (Math.random() * 400.f);
            points.add(point);
        }

        EXOAnimationGenerator wiggle = (EXOAnimationGenerator)EXOAnimationGenerator.create()
                .addAnimation(EXOAnimationElementRepeat.create(15, EXOAnimationElementWiggle.create(0, 0.75, 15.0)))
                .addAnimation(EXOAnimationElementRepeat.create(10, EXOAnimationElementJump.create(0, 0.5, 40.0).appendAnimation(EXOAnimationElementJump.create(0, 0.7, 80.0))))
                .addAnimation(EXOAnimationElementRepeat.create(10, EXOAnimationElementWobble.create(0, 1, 0.3).applyCurve(EXOAnimationCurveCosineInOut.create(0.5, 0.5))));

        double fps = 4.0;
        double wave = 0.3;

        runAnimation(blue, fps      ,wiggle.waitBefore(wave*0.0));
        runAnimation(green, fps     ,wiggle.waitBefore(wave*1.0));
        runAnimation(pink, fps      ,wiggle.waitBefore(wave*2.0));
        runAnimation(red, fps       ,wiggle.waitBefore(wave*3.0));
        runAnimation(yellow, fps    ,wiggle.waitBefore(wave*4.0));

        wave = 0.1;
        runAnimation(critter1, fps  ,wiggle.waitBefore(wave*0.0));
        runAnimation(critter2, fps  ,wiggle.waitBefore(wave*1.0));
        runAnimation(critter3, fps  ,wiggle.waitBefore(wave*2.0));
        runAnimation(critter4, fps  ,wiggle.waitBefore(wave*3.0));
 
        runAnimation(logo, 10.0, (EXOAnimationGenerator)EXOAnimationGenerator.create().addAnimation(EXOAnimationElementRepeat.create(10.0, EXOAnimationElementWiggle.create(0, 3, 2.5))));
        runAnimation(ray, fps,(EXOAnimationGenerator)EXOAnimationGenerator.create().addAnimation(EXOAnimationElementRotate.create(0, 80, 16)));
        runAnimation(kakaoText, fps,(EXOAnimationGenerator)EXOAnimationGenerator.create().addAnimation(EXOAnimationElementRepeat.create(10.0, EXOAnimationElementBlink.create(0, 1,0.2)).appendAnimation(wiggle)));

        tree1.anchorY = 1.0;
        tree2.anchorY = 1.0;
        tree3.anchorY = 1.0;
        runAnimation(tree1, fps,(EXOAnimationGenerator)EXOAnimationGenerator.create().addAnimation(EXOAnimationElementRepeat.create(10.0, EXOAnimationElementWiggle.create(0, 1,5.0))));
        runAnimation(tree2, fps,(EXOAnimationGenerator)EXOAnimationGenerator.create().addAnimation(EXOAnimationElementRepeat.create(10.0, EXOAnimationElementWiggle.create(0, 1,5.0))));
        runAnimation(tree3, fps,(EXOAnimationGenerator)EXOAnimationGenerator.create().addAnimation(EXOAnimationElementRepeat.create(10.0, EXOAnimationElementWiggle.create(0, 1,5.0))));

    }

    public EXOImageView addImage(final int x, final int y, final int resourceId, final EXOAnimationScreenConfig screen) {
        EXOImageView img = new EXOImageView(this);
        img.setScaleType(ImageView.ScaleType.FIT_XY); //(correct aspect ratio later)
        img.setImageResource(resourceId);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.width = (int) screen.scaleX(img.getDrawable().getIntrinsicWidth());
        lp.height = (int) screen.scaleY(img.getDrawable().getIntrinsicHeight());
        lp.setMargins((int) screen.scaleX(x) - lp.width / 2, (int) screen.scaleY(y) - lp.height / 2, -lp.width / 2, -lp.height / 2);
        img.setAdjustViewBounds(true);
        img.setLayoutParams(lp);
        layout.addView(img);
        return img;
    }

}
