package com.exozet.exoanimations.interpolation;

import com.exozet.exoanimations.IEXOAnimationCurveGetter;

public class EXOAnimationCurveLinearInOut implements IEXOAnimationCurveGetter {

    double fadeintill = 0.0;
    double fadeoutfrom = 1.0;

    @SuppressWarnings("unused")
    static EXOAnimationCurveLinearInOut create(double fadeintill, double fadeoutfrom) {
        EXOAnimationCurveLinearInOut ret = new EXOAnimationCurveLinearInOut();
        ret.fadeintill = fadeintill;
        ret.fadeoutfrom = fadeoutfrom;
        return ret;
    }

    @Override
    public double getInfluence(double t) {
        if (t < fadeintill)
            return Math.max(0.0, t / fadeintill);
        if (t > fadeoutfrom)
            return Math.min(1.0, 1.0 - (t - fadeoutfrom) / (1.0 - fadeoutfrom));
        return 1.0;
    }
}
