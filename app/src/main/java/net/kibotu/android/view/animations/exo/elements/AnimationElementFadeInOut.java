package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementFadeInOut extends AnimationElement {

    AnimationElementFadeInOut() {
        elementType = ElementType.fadeInOut;
    }

    static AnimationElementFadeInOut create(double startTime, double endTime) {
        AnimationElementFadeInOut ret = new AnimationElementFadeInOut();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.alpha = Math.sin(time / duration * Math.PI);
        return ret;
    }
}
