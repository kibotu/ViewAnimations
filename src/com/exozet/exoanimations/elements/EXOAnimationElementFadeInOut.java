package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementFadeInOut extends EXOAnimationElement {

    EXOAnimationElementFadeInOut() {
        elementType = ElementType.fadeInOut;
    }

    static EXOAnimationElementFadeInOut create(double startTime, double endTime) {
        EXOAnimationElementFadeInOut ret = new EXOAnimationElementFadeInOut();
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
