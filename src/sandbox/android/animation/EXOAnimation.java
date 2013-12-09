package sandbox.android.animation;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.animation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EXOAnimation {

    int tesselation = 50;
    ArrayList<Animation> animationParts = new ArrayList<Animation>();
    EXOAnimationState startState;

    public static EXOSpline spline = null;

    EXOAnimationState generateTempAnimation(EXOAnimationState before, EXOImageView image) {
        return addWobbleAnimation(before, 5.0, 0.1, 4.0, image);
    }

    EXOAnimationState addWobbleAnimation(EXOAnimationState before, double duration, double factor, double repeats, EXOImageView image) {
        startState = before;
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();
        double timeStep = 1.0 / (tesselation - 1);
        double durationStep = duration / (tesselation - 1) * 1000.0;
        double time = 0.0;
        double xScaleBefore = before.scaleX;
        double yScaleBefore = before.scaleY;
        double xMoveBefore = before.posX;
        double yMoveBefore = before.posY;
        PointF splBefore = new PointF(0.f, 0.f);
        if (spline != null) {
            splBefore = spline.pointAtTime(before.time);
        }

        for (int i = 0; i < tesselation; ++i) {
            double wobble = Math.sin(time * Math.PI * 2.0 * repeats) * factor;
            double xScale = before.scaleX + wobble;
            double yScale = before.scaleY / xScale;
            double xMove = before.posX + imageWidth * (1.0 - xScale) * 0.5;
            double yMove = before.posY + imageHeight * (1.0 - yScale) * 0.5;

            PointF spl = new PointF(0.f, 0.f);
            if (spline != null) {
                spl = spline.pointAtTime(before.time + time * duration);
            }

            Animation scaleAnimation = new ScaleAnimation((float) xScaleBefore, (float) xScale, (float) yScaleBefore, (float) yScale);
            Animation moveAnimation = new TranslateAnimation((float) xMoveBefore + splBefore.x, (float) xMove + spl.x, (float) yMoveBefore + splBefore.y, (float) yMove + spl.y);

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setDuration((long) durationStep);
            animationSet.setInterpolator(new LinearInterpolator());

            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(moveAnimation);

            animationSet.setFillAfter(true);
            animationSet.setFillBefore(true);
            animationSet.setFillEnabled(true);

            animationParts.add(animationSet);

            time += timeStep;
            xScaleBefore = xScale;
            yScaleBefore = yScale;
            xMoveBefore = xMove;
            yMoveBefore = yMove;
            splBefore = spl;
        }
        EXOAnimationState ret = EXOAnimationState.get(before.time + duration, xMoveBefore, yMoveBefore, xScaleBefore, yScaleBefore);
        return ret;
    }

    int getAnimationIndex(Animation animation) {
        for (int i = 0; i < animationParts.size(); ++i) {
            if (animation.equals(animationParts.get(i)))
                return i;
        }
        return -1;
    }

    boolean containsAnimation(Animation animation) {
        return getAnimationIndex(animation) != -1;
    }

    Animation next(Animation current) {
        int index = getAnimationIndex(current);
        if ((index == -1) || (index == animationParts.size() - 1))
            return null;
        return animationParts.get(index + 1);
    }
}

class EXOSpline implements Spline.Callback {
    ArrayList<PointF> linearSpline = new ArrayList<PointF>();
    final float fps = 1.0f / 25.0f;

    float getTimeScale() {
        return timeScale;
    }

    void setTimeScale(final float timeScale) {
        this.timeScale = timeScale;
    }

    private void setDuration(final float duration)
    {
        setTimeScale((float)(linearSpline.size()) / duration);
    }

    public float timeScale = 1.0f;

    PointF pointAtTime(double time) {
        time *= timeScale;
        if (linearSpline.isEmpty())
            return new PointF(0, 0);
        int iTime = (int) (time / fps);
        return linearSpline.get(iTime % linearSpline.size());
    }

    public void setValuesWithDuration(final List<PointF> points, final double duration) {
        //To change body of created methods use File | Settings | File Templates.
        Spline.doCubicHermiteSpline(points, fps, this);
        setDuration((float)duration);
    }

    @Override
    public void doCallback(final float x, final float y) {
        linearSpline.add(new PointF(x, y));
        //To change body of implemented methods use File | Settings | File Templates.
    }
}


class EXOAnimationState {
    double scaleX, scaleY;
    double posX, posY;
    double rotation;
    double alpha;
    double sheerX, sheerY;
    double time;

    static EXOAnimationState identity() {
        EXOAnimationState ret = new EXOAnimationState();

        ret.scaleX = ret.scaleY = 1.0;
        ret.posX = ret.posY = 0.0;
        ret.rotation = 0.0;
        ret.alpha = 1.0;
        ret.sheerX = ret.sheerY = 0.0;
        ret.time = 0;

        return ret;
    }

    static EXOAnimationState get(double time, double posX, double posY, double scaleX, double scaleY) {
        EXOAnimationState ret = new EXOAnimationState();

        ret.posX = posX;
        ret.posY = posY;
        ret.scaleX = scaleX;
        ret.scaleY = scaleY;
        ret.time = time;

        return ret;
    }
}

class EXOMultiAnimationQueue {
    LinkedList<Animation> currentlyPlayed = new LinkedList<Animation>();
    EXOImageView viewTheQueueRunsOn;
    Transformation restoreTransformation;

    void startAnimation(Animation animation) {
        if (animation != null)
            viewTheQueueRunsOn.startAnimation(animation);
    }

    void removeAnimation(Animation animation) {
        currentlyPlayed.remove(animation);
        //startAnimation(null);
    }
}

class EXOAnimationQueue implements Animation.AnimationListener {

    EXOMultiAnimationQueue multiQueue = new EXOMultiAnimationQueue();
    LinkedList<EXOAnimation> animationQueue = new LinkedList<EXOAnimation>();

    int currentIndex = 0;
    EXOAnimation currentActive;

    boolean looping = false;

    void run() {
        currentIndex = -1;
        next();
    }

    boolean next() {
        currentIndex++;
        if (currentIndex == animationQueue.size()) {
            if (looping) {
                run();
                return true;
            }
            return false;
        }

        currentActive = animationQueue.get(currentIndex);
        if (currentActive != null) {
            if (!currentActive.animationParts.isEmpty())
                multiQueue.startAnimation(currentActive.animationParts.get(0));
            else
                return next();
        }
        return true;
    }

    void add(EXOAnimation anim) {
        if (anim.animationParts.isEmpty())
            return;
        animationQueue.add(anim);
        for (int i = 0; i < anim.animationParts.size(); ++i) {
            // eine Animation kann im Moment nur einer Queue zugeordnet sein
            anim.animationParts.get(i).setAnimationListener(this);
        }
    }

    void startAnimation(EXOImageView toStartOn) {
        multiQueue.viewTheQueueRunsOn = toStartOn;
        //multiQueue.viewTheQueueRunsOn.queue = this;
        run();
    }

    @Override
    public void onAnimationStart(final Animation animation) {
    }

    @Override
    public void onAnimationEnd(final Animation animation) {

        if (currentActive.containsAnimation(animation)) {
            multiQueue.removeAnimation(animation);
            Animation nextOne = currentActive.next(animation);

            if (nextOne == null)
                next();
            else
                multiQueue.startAnimation(nextOne);
        }
    }

    @Override
    public void onAnimationRepeat(final Animation animation) {
    }
}
