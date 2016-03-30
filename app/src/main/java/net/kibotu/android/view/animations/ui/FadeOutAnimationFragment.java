package net.kibotu.android.view.animations.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import net.kibotu.android.view.animations.R;

import static net.kibotu.android.view.animations.R.id.btnStart;

/**
 * Created by Nyaruhodo on 30.03.2016.
 */
public class FadeOutAnimationFragment extends BaseFragment {

    @Bind(R.id.txtMessage)
    TextView txtMessage;

    // Animation
    Animation anim;

    @Override
    protected void onViewCreated() {

        // load the animation
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

        // set animation listener
        anim.setAnimationListener(this);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for fade in animation
        if (animation == anim) {
            Toast.makeText(getContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(btnStart)
    void onClick() {
        txtMessage.setVisibility(View.VISIBLE);

        // start the animation
        txtMessage.startAnimation(anim);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_fade_out;
    }
}
