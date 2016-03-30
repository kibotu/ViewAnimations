package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementRotate extends AnimationElement {

    double startAngle = 0.0;
    double endAngle = 0.0;

    AnimationElementRotate() {
        elementType = ElementType.rotate;
    }

    public static AnimationElementRotate create(double startTime, double endTime, double startAngle, double endAngle) {
        AnimationElementRotate ret = new AnimationElementRotate();
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