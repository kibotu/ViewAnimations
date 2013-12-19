package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementBlink extends EXOAnimationElement {

    double factor;

    EXOAnimationElementBlink() {
        elementType = ElementType.blink;
    }

    public static EXOAnimationElementBlink create(double startTime, double endTime, double factor) {
        EXOAnimationElementBlink ret = new EXOAnimationElementBlink();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.factor = factor;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.alpha = 1.0 + Math.cos(time / duration * Math.PI * 2.0) * factor * 0.5 - factor * 0.5;
        return ret;
    }
}
