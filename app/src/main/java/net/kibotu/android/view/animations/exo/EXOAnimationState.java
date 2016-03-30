package net.kibotu.android.view.animations.exo;

public class EXOAnimationState {
    public double scaleX;
    public double scaleY;
    public double posX;
    public double posY;
    public double rotation;
    public double alpha;
    double sheerX, sheerY;

    public static EXOAnimationState identity() {
        EXOAnimationState ret = new EXOAnimationState();

        ret.scaleX = ret.scaleY = 1.0;
        ret.posX = ret.posY = 0.0;
        ret.rotation = 0.0;
        ret.alpha = 1.0;
        ret.sheerX = ret.sheerY = 0.0;

        return ret;
    }

    public EXOAnimationState combine(EXOAnimationState second) {
        this.scaleX *= second.scaleX;
        this.scaleY *= second.scaleY;
        this.posX += second.posX;
        this.posY += second.posY;
        this.rotation += second.rotation;
        this.alpha *= second.alpha;
        this.sheerX += second.sheerX;
        this.sheerY += second.sheerY;
        return this;
    }

    public EXOAnimationState fadeCurve(double between0and1, IEXOAnimationCurveGetter curve) {
        if (curve != null) {
            double val = curve.getInfluence(between0and1);
            this.scaleX = (this.scaleX - 1.0) * val + 1.0;
            this.scaleY = (this.scaleY - 1.0) * val + 1.0;
            this.posX *= val;
            this.posY *= val;
            this.rotation *= val;
            this.alpha = (this.alpha - 1.0) * val + 1.0;
            this.sheerX *= val;
            this.sheerY *= val;
        }
        return this;
    }
}