package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementFadeIn extends EXOAnimationElement {

    EXOAnimationElementFadeIn() {
        elementType = ElementType.fadeIn;
    }

    public static EXOAnimationElementFadeIn create(double startTime, double endTime) {
        EXOAnimationElementFadeIn ret = new EXOAnimationElementFadeIn();
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
