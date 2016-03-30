package net.kibotu.android.view.animations.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import net.kibotu.android.view.animations.R;

/**
 * Created by Nyaruhodo on 30.03.2016.
 */
public class ComplexAnimationFragment extends BaseFragment {

    @Bind(R.id.txtMessage)
    TextView txtMessage;

    // Animation
    Animation anim;

    @Override
    protected void onViewCreated() {

        // load the animation
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.blink);

        // set animation listener
        anim.setAnimationListener(this);
    }

    @OnClick(R.id.btnStart)
    void onClick() {
        txtMessage.setVisibility(View.VISIBLE);

        // start the animation
        txtMessage.startAnimation(anim);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_blink;
    }
}
