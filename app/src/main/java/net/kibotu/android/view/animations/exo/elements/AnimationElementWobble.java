package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementWobble extends AnimationElement {
    double factor = 1.0;

    AnimationElementWobble() {
        elementType = ElementType.wobble;
    }

    public static AnimationElementWobble create(double startTime, double endTime, double factor) {
        AnimationElementWobble ret = new AnimationElementWobble();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.factor = factor;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        double wobble = Math.sin(time * Math.PI * 2.0 / duration) * factor;
        double xScale = 1.0 + wobble;
        double yScale = 1.0 / xScale;

        EXOAnimationState ret = EXOAnimationState.identity();
        ret.scaleX = xScale;
        ret.scaleY = yScale;

        return ret;
    }
}