package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementFade extends EXOAnimationElement {

    double from;
    double to;

    EXOAnimationElementFade() {
        elementType = ElementType.fadeIn;
    }

    static EXOAnimationElementFade create(double startTime, double endTime, double from, double to) {
        EXOAnimationElementFade ret = new EXOAnimationElementFade();
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
