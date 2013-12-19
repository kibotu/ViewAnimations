package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementRotate extends EXOAnimationElement {

    double startAngle = 0.0;
    double endAngle = 0.0;

    EXOAnimationElementRotate() {
        elementType = ElementType.rotate;
    }

    public static EXOAnimationElementRotate create(double startTime, double endTime, double startAngle, double endAngle) {
        EXOAnimationElementRotate ret = new EXOAnimationElementRotate();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.startAngle = startAngle;
        ret.endAngle = endAngle;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.rotation = (endAngle - startAngle) * time / duration + startAngle;
        return ret;
    }
}