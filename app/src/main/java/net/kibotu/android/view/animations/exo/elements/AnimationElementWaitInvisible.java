package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementWaitInvisible extends AnimationElement {
    AnimationElementWaitInvisible() {
        elementType = ElementType.waitInvisible;
    }

    public static AnimationElementWaitInvisible create(double startTime, double endTime) {
        AnimationElementWaitInvisible ret = new AnimationElementWaitInvisible();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.alpha = 0.0;
        return ret;
    }
}
