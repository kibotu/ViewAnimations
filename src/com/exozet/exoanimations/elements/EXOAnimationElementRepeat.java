package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.EXOAnimationState;
import com.exozet.exoanimations.EXOImageView;

public class EXOAnimationElementRepeat extends EXOAnimationElement {

    double repeats = 1.0;
    EXOAnimationElement anim = null;

    EXOAnimationElementRepeat() {
        elementType = ElementType.repeat;
    }

    public static EXOAnimationElementRepeat create(double repeats, EXOAnimationElement toRepeat) {
        EXOAnimationElementRepeat ret = new EXOAnimationElementRepeat();
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
            for (EXOAnimationElement element : elements) {
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
