package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementMove extends EXOAnimationElement {
    double fromX;
    double fromY;
    double toX;
    double toY;

    EXOAnimationElementMove() {
        elementType = ElementType.move;
    }

    public static EXOAnimationElementMove create(double startTime, double endTime, double fromX, double fromY, double toX, double toY) {
        EXOAnimationElementMove ret = new EXOAnimationElementMove();
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
        ret.posX = (toX - fromX) * time / duration + fromX;
        ret.posY = (toY - fromY) * time / duration + fromY;
        return ret;
    }
}