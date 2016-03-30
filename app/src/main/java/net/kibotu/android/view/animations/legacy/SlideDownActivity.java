package net.kibotu.android.view.animations.legacy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import net.kibotu.android.view.animations.R;

public class SlideDownActivity extends Activity implements AnimationListener {

    ImageView imgPoster;
    Button btnStart;

    // Animation
    Animation animSideDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_down);

        imgPoster = (ImageView) findViewById(R.id.imgLogo);
        btnStart = (Button) findViewById(R.id.btnStart);

        // load the animation
        animSideDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        // set animation listener
        animSideDown.setAnimationListener(this);

        // button click event
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // start the animation
                imgPoster.setVisibility(View.VISIBLE);
                imgPoster.startAnimation(animSideDown);
            }
        });

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for zoom in animation
        if (animation == animSideDown) {
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

}
