package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementWait extends AnimationElement {
    AnimationElementWait() {
        elementType = ElementType.wait;
    }

    static AnimationElementWait create(double startTime, double endTime) {
        AnimationElementWait ret = new AnimationElementWait();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        return EXOAnimationState.identity();
    }
}
