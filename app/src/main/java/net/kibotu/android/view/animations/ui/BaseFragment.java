package net.kibotu.android.view.animations.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import butterknife.ButterKnife;
import com.common.android.utils.interfaces.LayoutProvider;
import com.common.android.utils.interfaces.LogTag;

/**
 * Created by Nyaruhodo on 30.03.2016.
 */
public abstract class BaseFragment extends Fragment implements LogTag, LayoutProvider, Animation.AnimationListener {

    protected View rootView;

    @NonNull
    @Override
    public String tag() {
        return getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayout(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        onViewCreated();
    }

    protected abstract void onViewCreated();

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
