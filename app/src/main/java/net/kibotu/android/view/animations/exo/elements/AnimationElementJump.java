package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementJump extends AnimationElement {
    double height = 1.0;

    AnimationElementJump() {
        elementType = ElementType.rotate;
    }

    public static AnimationElementJump create(double startTime, double endTime, double height) {
        AnimationElementJump ret = new AnimationElementJump();
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
