package com.exozet.exoanimations;

import android.view.animation.*;
import com.exozet.exoanimations.elements.EXOAnimationElement;

import java.util.ArrayList;

public class EXOAnimationGenerator extends EXOAnimationElement {

    public static EXOAnimationScreenConfig screen = EXOAnimationScreenConfig.NO_SCALING;
    double waitBefore = 0.0;
    boolean waitBeforeHidden = false;
    public double timeScale = 1.0;

    public static EXOAnimationGenerator create() {
        return new EXOAnimationGenerator();
    }

    @SuppressWarnings("unused")
    static EXOAnimationGenerator create(EXOAnimationElement initialAnimation) {
        EXOAnimationGenerator ret = EXOAnimationGenerator.create();
        ret.addAnimation(initialAnimation);
        return ret;
    }

    void finalizeState(EXOAnimationState state) {
        //state.posX += state.scaleX * (1.0 - image.getWidth()) * 0.5;
        //state.posY += state.scaleY * (1.0 - image.getHeight()) * 0.5;
        state.posX = screen.animationPositionX(state.posX);
        state.posY = screen.animationPositionY(state.posY);
        state.scaleX = screen.animationScaleX(state.scaleX);
        state.scaleY = screen.animationScaleY(state.scaleY);
    }

    Animation generateAnimationFromTimeToTime(double time1, double time2, EXOImageView image, boolean restoreAlpha) {
        EXOAnimationState state1 = getStateForGlobalTime(time1, image);
        EXOAnimationState state2 = getStateForGlobalTime(time2, image);

        finalizeState(state1);
        finalizeState(state2);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration((long) ((time2 - time1) * 1000.0));
        animationSet.setInterpolator(new LinearInterpolator());

        boolean addedOne = false;
        if (!(eps(state1.scaleX, 1.0) && eps(state2.scaleX, 1.0) && eps(state1.scaleY, 1.0) && eps(state2.scaleY, 1.0))) {
            Animation scaleAnimation = new ScaleAnimation((float) state1.scaleX, (float) state2.scaleX, (float) state1.scaleY, (float) state2.scaleY, Animation.RELATIVE_TO_SELF, (float) image.anchorX, Animation.RELATIVE_TO_SELF, (float) image.anchorY);
            animationSet.addAnimation(scaleAnimation);
            addedOne = true;
        }

        if (!(eps(state1.rotation, 0.0) && eps(state2.rotation, 0.0))) {
            Animation rotateAnimation = new RotateAnimation((float) state1.rotation, (float) state2.rotation, Animation.RELATIVE_TO_SELF, (float) image.anchorX, Animation.RELATIVE_TO_SELF, (float) image.anchorY);
            animationSet.addAnimation(rotateAnimation);
            addedOne = true;
        }

        if (!(eps(state1.posX, 0.0) && eps(state2.posX, 0.0) && eps(state1.posY, 0.0) && eps(state2.posY, 0.0))) {
            Animation moveAnimation = new TranslateAnimation((float) state1.posX, (float) state2.posX, (float) state1.posY, (float) state2.posY);
            animationSet.addAnimation(moveAnimation);
            addedOne = true;
        }

        if ((!(eps(state1.alpha, 1.0) && eps(state2.alpha, 1.0))) || restoreAlpha) {
            Animation alphaAnimation = new AlphaAnimation((float) state1.alpha, (float) state2.alpha);
            if (restoreAlpha)
                alphaAnimation = new AlphaAnimation(0.9f, 1.f);
            animationSet.addAnimation(alphaAnimation);
            addedOne = true;
        }

        if (!addedOne) {
            // for empty stuffs
            Animation tempAnimation = new Animation() {
            };
            animationSet.addAnimation(tempAnimation);
        }

        if (addedOne) {
            animationSet.setFillAfter(true);
            animationSet.setFillBefore(true);
            animationSet.setFillEnabled(true);
        }
        return animationSet;
    }

    private boolean eps(final double val1, final double val2) {
        return Math.abs(val2 - val1) < 0.001;
    }

    @Override
    public EXOAnimationState getStateForGlobalTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        for (EXOAnimationElement element : elements) {
            EXOAnimationState state = element.getStateForGlobalTime(time, image);
            if (state != null)
                ret.combine(state);
        }
        return ret;
    }

    double endTime;

    public void determineEndTime() {
        for (EXOAnimationElement element : elements) {
            double endHere = element.startTime + element.getDuration();
            if (endHere > endTime) endTime = endHere;
        }
    }

    Animation generateAnimationNr(int nr, double timeDelta, EXOImageView image) {
        if (nr == 0) {
            // this can lead to problems, since you must visit the 0 in anycase when calculating the animation
            // if you access the nr randomly (e.g. not starting from 0/-1) then call determineEndTime for yourself
            determineEndTime();
        }

        if (nr == -1) {
            if (!eps(this.waitBefore * this.timeScale, 0.0)) {
                Animation tempAnimation = new Animation() {
                };
                if (waitBeforeHidden)
                    tempAnimation = new AlphaAnimation(0, 0);
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.setDuration((long) (this.waitBefore * this.timeScale * 1000.0));
                animationSet.setInterpolator(new LinearInterpolator());
                animationSet.addAnimation(tempAnimation);
                return animationSet;
            }
            return null;
        }

        double from = nr * timeDelta * this.timeScale;
        double to = (nr + 1) * timeDelta * this.timeScale;
        if (nr >= 0 && to <= endTime) {
            return generateAnimationFromTimeToTime(from, to, image, (nr == 0) && waitBeforeHidden);
        }
        if (from < endTime && to >= endTime) {
            return generateAnimationFromTimeToTime(from, endTime, image, (nr == 0) && waitBeforeHidden);
        }

        return null;
    }

    ArrayList<Animation> generateWholeAnimation(double timeDelta, EXOImageView image) {
        ArrayList<Animation> ret = new ArrayList<Animation>();
        Animation before = generateAnimationNr(-1, timeDelta, image);
        if (before != null)
            ret.add(before);
        int nr = 0;
        Animation anim = generateAnimationNr(nr, timeDelta, image);
        while (anim != null) {
            ret.add(anim);
            nr++;
            anim = generateAnimationNr(nr, timeDelta, image);
        }

        return ret;
    }

    public EXOAnimationGenerator preDelay(double time, boolean hidden) {
        EXOAnimationGenerator cloned = this.clone();
        cloned.waitBefore = time;
        cloned.waitBeforeHidden = hidden;
        return cloned; // it's not really cloned :(
    }

    @Override
    public EXOAnimationGenerator clone() {
        EXOAnimationGenerator ret = new EXOAnimationGenerator();
        ret.elements = new ArrayList<EXOAnimationElement>(elements); // the elements itself aren't cloned
        ret.waitBefore = waitBefore;
        return ret;
    }
}
