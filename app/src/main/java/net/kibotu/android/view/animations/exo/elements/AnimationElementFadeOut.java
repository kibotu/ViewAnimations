package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementFadeOut extends AnimationElement {

    AnimationElementFadeOut() {
        elementType = ElementType.fadeOut;
    }

    public static AnimationElementFadeOut create(double startTime, double endTime) {
        AnimationElementFadeOut ret = new AnimationElementFadeOut();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.alpha = 1.0 - time / duration;
        return ret;
    }
}
