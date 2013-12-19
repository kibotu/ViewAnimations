package com.exozet.exoanimations.interpolation;

import com.exozet.exoanimations.IEXOAnimationCurveGetter;

public class EXOAnimationCurveSin implements IEXOAnimationCurveGetter {

    static public EXOAnimationCurveSin create() {
        return new EXOAnimationCurveSin();
    }

    @Override
    public double getInfluence(double t) {
        return Math.sin(t * Math.PI); // fades from 0 to 1 and back
    }
}