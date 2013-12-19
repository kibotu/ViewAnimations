package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementWiggle extends EXOAnimationElement {

    double angleDelta;

    EXOAnimationElementWiggle() {
        elementType = ElementType.wiggle;
    }

    public static EXOAnimationElementWiggle create(double startTime, double endTime, double angleDelta) {
        EXOAnimationElementWiggle ret = new EXOAnimationElementWiggle();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.angleDelta = angleDelta;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.rotation = Math.sin(time / duration * 2.0 * Math.PI) * angleDelta;
        return ret;
    }
}
