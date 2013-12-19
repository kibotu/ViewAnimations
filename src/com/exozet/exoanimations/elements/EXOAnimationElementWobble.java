package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementWobble extends EXOAnimationElement {
    double factor = 1.0;

    EXOAnimationElementWobble() {
        elementType = ElementType.wobble;
    }

    public static EXOAnimationElementWobble create(double startTime, double endTime, double factor) {
        EXOAnimationElementWobble ret = new EXOAnimationElementWobble();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.factor = factor;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        double wobble = Math.sin(time * Math.PI * 2.0 / duration) * factor;
        double xScale = 1.0 + wobble;
        double yScale = 1.0 / xScale;

        EXOAnimationState ret = EXOAnimationState.identity();
        ret.scaleX = xScale;
        ret.scaleY = yScale;

        return ret;
    }
}