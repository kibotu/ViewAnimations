package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementBlink extends AnimationElement {

    double factor;

    AnimationElementBlink() {
        elementType = ElementType.blink;
    }

    public static AnimationElementBlink create(double startTime, double endTime, double factor) {
        AnimationElementBlink ret = new AnimationElementBlink();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.factor = factor;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.alpha = 1.0 + Math.cos(time / duration * Math.PI * 2.0) * factor * 0.5 - factor * 0.5;
        return ret;
    }
}
