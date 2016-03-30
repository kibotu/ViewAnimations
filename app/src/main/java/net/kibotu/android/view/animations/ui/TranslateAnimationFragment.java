package net.kibotu.android.view.animations.ui;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.OnClick;
import net.kibotu.android.view.animations.R;

import static net.kibotu.android.view.animations.R.id.btnStart;

/**
 * Created by Nyaruhodo on 30.03.2016.
 */
public class TranslateAnimationFragment extends BaseFragment {

    @Bind(R.id.imgLogo)
    ImageView imgLogo;

    // Animation
    Animation anim;

    @Override
    protected void onViewCreated() {

        // load the animation
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.translate);

        // set animation listener
        anim.setAnimationListener(this);
    }

    @OnClick(btnStart)
    void onClick() {
        imgLogo.startAnimation(anim);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_translate;
    }
}

