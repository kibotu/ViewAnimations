package net.kibotu.android.view.animations.exo;

import android.view.animation.Animation;

import java.util.ArrayList;

public class EXOAnimationQueue implements Animation.AnimationListener {

    ArrayList<Animation> animations = new ArrayList<Animation>();
    int currentIndex;
    public boolean looping = false;
    EXOImageView viewToRunOn;
    Animation currentActive;
    boolean dynamic = true;
    double generatorFPS;
    AnimationGenerator generator;

    public void generateWithCollection(AnimationGenerator animation, EXOImageView toStartOn, double fps) {
        viewToRunOn = toStartOn;
        if (!dynamic)
            animations = animation.generateWholeAnimation(1.0 / fps, viewToRunOn);
        else {
            generator = animation;
            generatorFPS = fps;
        }
    }

    public void run() {
        currentIndex = -1;
        next();
    }

    boolean next() {
        currentIndex++;
        if (!dynamic) {
            if (currentIndex == animations.size()) {
                if (looping) {
                    run();
                    return true;
                }
                return false;
            }
        }

        if (currentActive != null)
            currentActive.setAnimationListener(null);

        if (!dynamic)
            currentActive = animations.get(currentIndex);
        else {
            // sometimes we have a pause frame as -1 element
            currentActive = generator.generateAnimationNr(currentIndex - 1, 1.0 / generatorFPS, viewToRunOn);
            if (currentIndex == 0 && currentActive == null) {
                currentIndex++;
                currentActive = generator.generateAnimationNr(currentIndex - 1, 1.0 / generatorFPS, viewToRunOn);
            }
        }

        if (currentActive != null) {
            currentActive.setAnimationListener(this);
            viewToRunOn.clearAnimation();
            viewToRunOn.startAnimation(currentActive);
        } else {
            if (dynamic) {
                run();
                return true;
            } else
                return next();
        }
        return true;
    }

    @Override
    public void onAnimationStart(final Animation animation) {
    }

    @Override
    public void onAnimationEnd(final Animation animation) {
        if (currentActive == animation)
            next();
    }

    @Override
    public void onAnimationRepeat(final Animation animation) {
    }
}