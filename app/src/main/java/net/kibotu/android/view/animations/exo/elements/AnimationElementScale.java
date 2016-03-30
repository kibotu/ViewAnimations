package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementScale extends AnimationElement {
    double fromX;
    double fromY;
    double toX;
    double toY;

    AnimationElementScale() {
        elementType = ElementType.scale;
    }

    public static AnimationElementScale create(double startTime, double endTime, double fromX, double fromY, double toX, double toY) {
        AnimationElementScale ret = new AnimationElementScale();
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
