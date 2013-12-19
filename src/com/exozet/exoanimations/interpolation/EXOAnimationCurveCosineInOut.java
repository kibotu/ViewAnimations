package com.exozet.exoanimations.interpolation;

import com.exozet.exoanimations.IEXOAnimationCurveGetter;

public class EXOAnimationCurveCosineInOut implements IEXOAnimationCurveGetter {

    double fadeintill = 0.0;
    double fadeoutfrom = 1.0;

    public static EXOAnimationCurveLinearInOut create(double fadeintill, double fadeoutfrom) {
        EXOAnimationCurveLinearInOut ret = new EXOAnimationCurveLinearInOut();
        ret.fadeintill = fadeintill;
        ret.fadeoutfrom = fadeoutfrom;
        return ret;
    }

    double formula(double in) {
        return 1.0 - (0.5 + Math.cos(in * Math.PI) * 0.5);
    }

    @Override
    public double getInfluence(double t) {
        if (t < fadeintill)
            return formula(Math.max(0.0, t / fadeintill));
        if (t > fadeoutfrom)
            return formula(Math.min(1.0, 1.0 - (t - fadeoutfrom) / (1.0 - fadeoutfrom)));
        return 1.0;
    }
}
