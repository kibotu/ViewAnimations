package net.kibotu.android.view.animations.exo.interpolation;

import net.kibotu.android.view.animations.exo.IEXOAnimationCurveGetter;

public class EXOAnimationCurveSin implements IEXOAnimationCurveGetter {

    static public EXOAnimationCurveSin create() {
        return new EXOAnimationCurveSin();
    }

    @Override
    public double getInfluence(double t) {
        return Math.sin(t * Math.PI); // fades from 0 to 1 and back
    }
}