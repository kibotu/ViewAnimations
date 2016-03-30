package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementFadeIn extends AnimationElement {

    AnimationElementFadeIn() {
        elementType = ElementType.fadeIn;
    }

    public static AnimationElementFadeIn create(double startTime, double endTime) {
        AnimationElementFadeIn ret = new AnimationElementFadeIn();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.alpha = time / duration;
        return ret;
    }
}
