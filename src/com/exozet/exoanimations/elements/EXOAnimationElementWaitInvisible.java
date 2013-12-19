package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementWaitInvisible extends EXOAnimationElement {
    EXOAnimationElementWaitInvisible() {
        elementType = ElementType.waitInvisible;
    }

    public static EXOAnimationElementWaitInvisible create(double startTime, double endTime) {
        EXOAnimationElementWaitInvisible ret = new EXOAnimationElementWaitInvisible();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.alpha = 0.0;
        return ret;
    }
}
