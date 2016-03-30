package net.kibotu.android.view.animations.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import net.kibotu.android.view.animations.R;

import static net.kibotu.android.view.animations.R.id.btnStart;

/**
 * Created by Nyaruhodo on 30.03.2016.
 */
public class CrossFadeAnimationFragment extends BaseFragment {

    @Bind(R.id.txtMessage1)
    TextView txtMessage1;
    @Bind(R.id.txtMessage2)
    TextView txtMessage2;

    // Animation
    Animation animFadeIn;
    Animation animFadeOut;

    @Override
    protected void onViewCreated() {

        // load animations
        animFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

        // set animation listeners
        animFadeIn.setAnimationListener(this);
        animFadeOut.setAnimationListener(this);
    }

    @OnClick(btnStart)
    void onClick() {
        // make fade in element visible
        txtMessage2.setVisibility(View.VISIBLE);
        // start fade in animation
        txtMessage2.startAnimation(animFadeIn);

        // start fade out animation
        txtMessage1.startAnimation(animFadeOut);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // if animation is fade out hide them after completing animation
        if (animation == animFadeOut) {

            // hide faded out element
            txtMessage1.setVisibility(View.GONE);
        }

        if (animation == animFadeIn) {
            // do something after fade in completed

            // set visibility of fade in element
            txtMessage2.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_crossfade;
    }
}
