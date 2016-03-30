package net.kibotu.android.view.animations.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import net.kibotu.android.view.animations.R;

/**
 * Created by Nyaruhodo on 30.03.2016.
 */
public class BounceAnimationFragment extends BaseFragment {

    @Bind(R.id.imgLogo)
    ImageView imgPoster;

    // Animation
    Animation anim;

    @Override
    protected void onViewCreated() {

        // load the animation
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);

        // set animation listener
        anim.setAnimationListener(this);
    }

    @OnClick(R.id.btnStart)
    void onClick() {
        imgPoster.setVisibility(View.VISIBLE);
        imgPoster.startAnimation(anim);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_bounce;
    }
}
