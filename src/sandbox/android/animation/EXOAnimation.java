package sandbox.android.animation;

import android.graphics.PointF;
import android.view.animation.*;

import java.util.ArrayList;
import java.util.List;

interface AnimStateGetter
{
    public EXOAnimationState stateAtTime(double time, EXOImageView image);
}

class EXOAnimationState {
    double scaleX, scaleY;
    double posX, posY;
    double rotation;
    double alpha;
    double sheerX, sheerY;

    static EXOAnimationState identity() {
        EXOAnimationState ret = new EXOAnimationState();

        ret.scaleX = ret.scaleY = 1.0;
        ret.posX = ret.posY = 0.0;
        ret.rotation = 0.0;
        ret.alpha = 1.0;
        ret.sheerX = ret.sheerY = 0.0;

        return ret;
    }

    public void combine(EXOAnimationState second)
    {
        this.scaleX *= second.scaleX;
        this.scaleY *= second.scaleY;
        this.posX += second.posX;
        this.posY += second.posY;
        this.rotation += second.rotation;
        this.alpha *= second.alpha;
        this.sheerX += second.sheerX;
        this.sheerY += second.sheerY;
    }

}

class EXOAnimationElement implements AnimStateGetter
{
    enum ElementType
    {
        pure,
        spline,
        wobble
    }
    double startTime;
    double duration;
    ElementType elementType;

    EXOAnimationElement()
    {
        elementType = ElementType.pure;
        startTime = 0.0;
        duration = 0.0;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image)
    {
        return EXOAnimationState.identity();
    }

    public EXOAnimationState getStateForGlobalTime(double time, EXOImageView image)
    {
        if (time >= startTime && time < startTime + duration)
            return stateAtTime(time-startTime,image);
        return null;
    }
}

class EXOAnimationElementSpline extends EXOAnimationElement implements Spline.Callback
{
    EXOAnimationElementSpline()
    {
        elementType = ElementType.spline;
    }

    static EXOAnimationElementSpline create(double startTime, double endTime, List<PointF> points)
    {
        EXOAnimationElementSpline ret = new EXOAnimationElementSpline();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.points = points;

        ret.linearSpline.clear();
        Spline.doCubicHermiteSpline(points, ret.fps, ret);
        ret.setSplineDuration((float) duration);

        return ret;
    } 
    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image)
    {
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

    protected float getTimeScale() {
        return timeScale;
    }
    protected void setTimeScale(final float timeScale) {
        this.timeScale = timeScale;
    }
    protected void setSplineDuration(final float duration)
    {
        setTimeScale((float)(linearSpline.size()) / duration);
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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

class EXOAnimationElementWobble extends EXOAnimationElement
{
    double repeats = 1.0;
    double factor = 1.0;

    EXOAnimationElementWobble()
    {
        elementType = ElementType.wobble;
    }

    static EXOAnimationElementWobble create(double startTime, double endTime, double factor, double repeats)
    {
        EXOAnimationElementWobble ret = new EXOAnimationElementWobble();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.factor = factor;
        ret.repeats = repeats;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image)
    {
        double wobble = Math.sin(time * Math.PI * 2.0 * repeats / duration) * factor;
        double xScale = 1.0 + wobble;
        double yScale = 1.0 / xScale;

        EXOAnimationState ret = EXOAnimationState.identity();
        ret.scaleX = xScale;
        ret.scaleY = yScale;

        return ret;
    }
}

class EXOAnimationCollection
{
    ArrayList<EXOAnimationElement> elements = new ArrayList();

    void addElement(EXOAnimationElement element)
    {
        elements.add(element);
    }

    EXOAnimationState getCombinedStateAtTime(double time, EXOImageView image)
    {
        EXOAnimationState ret = EXOAnimationState.identity();
        for (int i = 0; i < elements.size(); ++i)
        {
            EXOAnimationElement element = elements.get(i);

            EXOAnimationState state = element.getStateForGlobalTime(time,image);
            if (state != null)
                ret.combine(state);
        }
        return ret;
    }

    void fixStateAnchorPoint(EXOAnimationState state, EXOImageView image)
    {
        state.posX += state.scaleX * (1.0 - image.getWidth()) * 0.5;
        state.posY += state.scaleY * (1.0 - image.getHeight()) * 0.5;
    }

    Animation generateAnimationFromTimeToTime(double time1, double time2, EXOImageView image)
    {
        EXOAnimationState state1 = getCombinedStateAtTime(time1, image);
        EXOAnimationState state2 = getCombinedStateAtTime(time2, image);

        fixStateAnchorPoint(state1,image);
        fixStateAnchorPoint(state2,image);

        Animation scaleAnimation    = new ScaleAnimation    ((float)state1.scaleX, (float)state2.scaleX, (float)state1.scaleY,(float)state2.scaleY);
        Animation moveAnimation     = new TranslateAnimation((float)state1.posX,   (float)state2.posX,   (float)state1.posY,  (float)state2.posY);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration((long) ((time2-time1)*1000.0));
        animationSet.setInterpolator(new LinearInterpolator());

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(moveAnimation);

        animationSet.setFillAfter(true);
        animationSet.setFillBefore(true);
        animationSet.setFillEnabled(true);

        return animationSet;
    }

    ArrayList<Animation> generateWholeAnimation(double timeDelta,EXOImageView image)
    {
        double startTime = 0.0;
        double endTime = 0.0;
        for (int i = 0; i < elements.size(); ++i)
        {
            EXOAnimationElement element = elements.get(i);
            double endHere = element.startTime + element.duration;

            if (endHere > endTime) endTime = endHere;
        }

        ArrayList<Animation> ret = new ArrayList<Animation>();
        for (double time = startTime; time < endTime; time += timeDelta)
        {
            Animation anim = generateAnimationFromTimeToTime(time,time+timeDelta,image);
            ret.add(anim);
        }

        return ret;
    }
}

class EXOAnimationQueue implements Animation.AnimationListener {

    ArrayList<Animation> animations = new ArrayList<Animation>();
    int currentIndex;
    boolean looping = false;
    EXOImageView viewToRunOn;
    Animation currentActive;
    final double fps = 1.0 / 25.0;

    void generateWithCollection(EXOAnimationCollection collection,EXOImageView toStartOn)
    {
        viewToRunOn = toStartOn;
        animations = collection.generateWholeAnimation(fps,viewToRunOn);
    }

    void run()
    {
        currentIndex = -1;
        next();
    }

    boolean next()
    {
        currentIndex++;
        if (currentIndex == animations.size()) {
            if (looping) {
                run();
                return true;
            }
            return false;
        }

        currentActive = animations.get(currentIndex);
        if (currentActive != null)
        {
            currentActive.setAnimationListener(this);
            viewToRunOn.startAnimation(currentActive);
        }
        else
            return next();
        return true;
    }

    @Override
    public void onAnimationStart(final Animation animation)
    {
    }

    @Override
    public void onAnimationEnd(final Animation animation)
    {
        if (currentActive == animation)
            next();
    }

    @Override
    public void onAnimationRepeat(final Animation animation)
    {
    }
}
