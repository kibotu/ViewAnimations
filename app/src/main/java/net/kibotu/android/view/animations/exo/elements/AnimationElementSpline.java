package net.kibotu.android.view.animations.exo.elements;

import android.graphics.PointF;
import net.kibotu.android.view.animations.exo.EXOAnimationState;
import net.kibotu.android.view.animations.exo.EXOImageView;
import net.kibotu.android.view.animations.exo.Spline;

import java.util.ArrayList;
import java.util.List;

public class AnimationElementSpline extends AnimationElement implements Spline.Callback {

    AnimationElementSpline() {
        elementType = ElementType.spline;
    }

    static AnimationElementSpline create(double startTime, double endTime, List<PointF> points) {
        AnimationElementSpline ret = new AnimationElementSpline();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.points = points;

        ret.linearSpline.clear();
        Spline.doCubicHermiteSpline(points, ret.fps, ret);
        ret.setSplineDuration((float) ret.duration);

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        PointF p = pointAtTime(time);
        ret.posX = p.x;
        ret.posY = p.y;
        return ret;
    }

    protected ArrayList<PointF> linearSpline = new ArrayList<PointF>();
    protected float timeScale = 1.0f;
    protected float fps = 1.0f / 25.0f;

    List<PointF> points;

    @SuppressWarnings("unused")
    protected float getTimeScale() {
        return timeScale;
    }

    protected void setTimeScale(final float timeScale) {
        this.timeScale = timeScale;
    }

    protected void setSplineDuration(final float duration) {
        setTimeScale((float) (linearSpline.size()) / duration);
    }

    protected PointF pointAtTime(double time) {
        time *= timeScale;
        if (linearSpline.isEmpty())
            return new PointF(0, 0);
        int iTime = (int) (time / fps);
        return linearSpline.get(iTime % linearSpline.size());
    }

    @Override
    public void doCallback(final float x, final float y) {
        linearSpline.add(new PointF(x, y));
    }
}
