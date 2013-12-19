package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementScale extends EXOAnimationElement {
    double fromX;
    double fromY;
    double toX;
    double toY;

    EXOAnimationElementScale() {
        elementType = ElementType.scale;
    }

    public static EXOAnimationElementScale create(double startTime, double endTime, double fromX, double fromY, double toX, double toY) {
        EXOAnimationElementScale ret = new EXOAnimationElementScale();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.fromX = fromX;
        ret.fromY = fromY;
        ret.toX = toX;
        ret.toY = toY;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.scaleX = (toX - fromX) * time / duration + fromX;
        ret.scaleY = (toY - fromY) * time / duration + fromY;
        return ret;
    }
}
