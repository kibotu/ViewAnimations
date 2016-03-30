package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;

public class AnimationElementRepeat extends AnimationElement {

    double repeats = 1.0;
    AnimationElement anim = null;

    AnimationElementRepeat() {
        elementType = ElementType.repeat;
    }

    public static AnimationElementRepeat create(double repeats, AnimationElement toRepeat) {
        AnimationElementRepeat ret = new AnimationElementRepeat();
        ret.repeats = repeats;
        ret.startTime = 0.0;
        ret.duration = 0.0;
        ret.anim = toRepeat;
        return ret;
    }

    public double getDuration() {
        return Math.max(this.overallDuration, anim.getDuration()) * repeats;
    }

    @Override
    public EXOAnimationState getStateForGlobalTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        if (anim != null && anim != this) {
            if (time >= startTime && time < startTime + this.getDuration()) {
                double into = (time - startTime) % Math.max(this.overallDuration, anim.getDuration()); // ich verstehe den unterschied zwischen anim.starttime und this.starttime nicht, aber funktioniert erstmal
                if (into < anim.getDuration()) {
                    EXOAnimationState state = anim.getStateForGlobalTime(into + anim.startTime, image).fadeCurve((into - anim.startTime) / this.getDuration(), fadeCurve);
                    if (state != null)
                        ret.combine(state);
                }
            }
        }

        if (time >= startTime && time < startTime + this.getDuration()) {
            for (AnimationElement element : elements) {
                if (element != null && element != this) {
                    EXOAnimationState state = element.getStateForGlobalTime(time, image);
                    if (state != null)
                        ret.combine(state);
                }
            }
        }
        return ret;
    }
}
