package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementWait extends EXOAnimationElement {
    EXOAnimationElementWait() {
        elementType = ElementType.wait;
    }

    static EXOAnimationElementWait create(double startTime, double endTime) {
        EXOAnimationElementWait ret = new EXOAnimationElementWait();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        return EXOAnimationState.identity();
    }
}
