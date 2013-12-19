package com.exozet.exoanimations.elements;

import com.exozet.exoanimations.*;

import java.util.ArrayList;

public class EXOAnimationElement implements IAnimStateGetter, IAnimStateGetterGlobal {

    public double startTime;
    protected double duration;
    protected double overallDuration;
    protected ArrayList<EXOAnimationElement> elements = new ArrayList<EXOAnimationElement>();
    ElementType elementType;
    IEXOAnimationCurveGetter fadeCurve;

    public EXOAnimationElement() {
        elementType = ElementType.pure;
        startTime = 0.0;
        duration = 0.0;
        overallDuration = 0.0;
    }

    public EXOAnimationElement applyCurve(IEXOAnimationCurveGetter curve) {
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

    public EXOAnimationElement appendAnimation(EXOAnimationElement element) {
        element.startTime = this.startTime + this.getDuration();
        this.elements.add(element);
        double elementLength = element.startTime + element.getDuration();
        double thisLength = this.startTime + this.getDuration();
        if (elementLength > thisLength)
            this.overallDuration = elementLength - this.startTime;
        return this;
    }

    public EXOAnimationElement addAnimation(EXOAnimationElement element) {
        this.elements.add(element);
        double elementLength = element.startTime + element.getDuration();
        double thisLength = this.startTime + this.getDuration();
        if (elementLength > thisLength)
            this.overallDuration = elementLength - this.startTime;
        return this;
    }

    @SuppressWarnings("unused")
    public EXOAnimationElement addAnimationAndMakeGlobal(EXOAnimationElement element) {
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
            for (EXOAnimationElement element : elements) {
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
