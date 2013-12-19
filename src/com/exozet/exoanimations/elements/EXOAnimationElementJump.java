package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementJump extends EXOAnimationElement {
    double height = 1.0;

    EXOAnimationElementJump() {
        elementType = ElementType.rotate;
    }

    public static EXOAnimationElementJump create(double startTime, double endTime, double height) {
        EXOAnimationElementJump ret = new EXOAnimationElementJump();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.height = height;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.posY = -Math.sin(time / duration * Math.PI) * height;
        return ret;
    }
}
