package net.kibotu.android.view.animations.exo.elements;

import net.kibotu.android.view.animations.exo.*;

import java.util.ArrayList;

public class AnimationElement implements IAnimStateGetter, IAnimStateGetterGlobal {

    public double startTime;
    protected double duration;
    protected double overallDuration;
    protected ArrayList<AnimationElement> elements = new ArrayList<AnimationElement>();
    ElementType elementType;
    IEXOAnimationCurveGetter fadeCurve;

    public AnimationElement() {
        elementType = ElementType.pure;
        startTime = 0.0;
        duration = 0.0;
        overallDuration = 0.0;
    }

    public AnimationElement applyCurve(IEXOAnimationCurveGetter curve) {
        fadeCurve = curve;
        return this;
    }

    public double getDuration() {
        return Math.max(overallDuration, duration);
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        return EXOAnimationState.identity();
    }

    public AnimationElement appendAnimation(AnimationElement element) {
        element.startTime = this.startTime + this.getDuration();
        this.elements.add(element);
        double elementLength = element.startTime + element.getDuration();
        double thisLength = this.startTime + this.getDuration();
        if (elementLength > thisLength)
            this.overallDuration = elementLength - this.startTime;
        return this;
    }

    public AnimationElement addAnimation(AnimationElement element) {
        this.elements.add(element);
        double elementLength = element.startTime + element.getDuration();
        double thisLength = this.startTime + this.getDuration();
        if (elementLength > thisLength)
            this.overallDuration = elementLength - this.startTime;
        return this;
    }

    @SuppressWarnings("unused")
    public AnimationElement addAnimationAndMakeGlobal(AnimationElement element) {
        this.elements.add(element);
        element.duration = 1000000.0;
        element.startTime = 0.0;
        return this;
    }

    @Override
    public EXOAnimationState getStateForGlobalTime(double time, EXOImageView image) {

        EXOAnimationState ret = null;
        if (time >= startTime && time < startTime + duration) {
            ret = stateAtTime(time - startTime, image).fadeCurve((time - startTime) / duration, fadeCurve);
        }
        if (time >= startTime && time < startTime + getDuration()) {
            for (AnimationElement element : elements) {
                if (element != null && element != this) {
                    EXOAnimationState state = element.getStateForGlobalTime(time, image);
                    if (state != null) {
                        if (ret == null)
                            ret = state;
                        else
                            ret.combine(state);
                    }
                }
            }
        }
        return ret;
    }

    enum ElementType {
        pure,
        spline,
        wobble,
        rotate,
        repeat,
        sequence,
        wiggle,
        blink,
        fadeIn,
        fadeOut,
        fadeInOut,
        waitInvisible,
        wait,
        move,
        scale
    }
}
