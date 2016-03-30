package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementFade extends AnimationElement {

    double from;
    double to;

    AnimationElementFade() {
        elementType = ElementType.fadeIn;
    }

    static AnimationElementFade create(double startTime, double endTime, double from, double to) {
        AnimationElementFade ret = new AnimationElementFade();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.from = from;
        ret.to = to;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.alpha = (to - from) * time / duration + from;
        return ret;
    }
}
