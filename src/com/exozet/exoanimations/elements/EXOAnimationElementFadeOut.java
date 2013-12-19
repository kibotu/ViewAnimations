package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementFadeOut extends EXOAnimationElement {

    EXOAnimationElementFadeOut() {
        elementType = ElementType.fadeOut;
    }

    public static EXOAnimationElementFadeOut create(double startTime, double endTime) {
        EXOAnimationElementFadeOut ret = new EXOAnimationElementFadeOut();
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
