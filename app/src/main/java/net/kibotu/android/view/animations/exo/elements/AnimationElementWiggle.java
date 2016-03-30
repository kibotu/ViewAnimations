package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementWiggle extends AnimationElement {

    double angleDelta;

    AnimationElementWiggle() {
        elementType = ElementType.wiggle;
    }

    public static AnimationElementWiggle create(double startTime, double endTime, double angleDelta) {
        AnimationElementWiggle ret = new AnimationElementWiggle();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.angleDelta = angleDelta;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.rotation = Math.sin(time / duration * 2.0 * Math.PI) * angleDelta;
        return ret;
    }
}
