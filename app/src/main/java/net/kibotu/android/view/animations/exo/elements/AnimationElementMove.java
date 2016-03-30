package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementMove extends AnimationElement {
    double fromX;
    double fromY;
    double toX;
    double toY;

    AnimationElementMove() {
        elementType = ElementType.move;
    }

    public static AnimationElementMove create(double startTime, double endTime, double fromX, double fromY, double toX, double toY) {
        AnimationElementMove ret = new AnimationElementMove();
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