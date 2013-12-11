package sandbox.android.animation;

import android.graphics.PointF;
import android.view.animation.*;

import java.util.ArrayList;
import java.util.List;

interface AnimStateGetter {
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

    public EXOAnimationState fadeCurve(double between0and1, EXOAnimationCurveGetter curve) {
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

interface EXOAnimationCurveGetter {
    double getInfluence(double t);
}

class EXOAnimationCurveSin implements EXOAnimationCurveGetter {
    @Override
    public double getInfluence(double t) {
        return Math.sin(t * Math.PI); // fades from 0 to 1 and back
    }
}

class EXOAnimationCurveLinearInOut implements EXOAnimationCurveGetter {
    double fadeintill = 0.0;
    double fadeoutfrom = 1.0;

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

class EXOAnimationCurveCosineInOut implements EXOAnimationCurveGetter {
    double fadeintill = 0.0;
    double fadeoutfrom = 1.0;

    static EXOAnimationCurveLinearInOut create(double fadeintill, double fadeoutfrom) {
        EXOAnimationCurveLinearInOut ret = new EXOAnimationCurveLinearInOut();
        ret.fadeintill = fadeintill;
        ret.fadeoutfrom = fadeoutfrom;
        return ret;
    }

    double formula(double in)
    {
        return 1.0 - (0.5 + Math.cos(in * Math.PI)*0.5);
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


enum EXOAnimationScreenConfig
{
    x320(0.2083, 0.208333),
    x480(0.3125, 0.3125),
    x540(0.3515625, 0.35156),
    x600(0.390625, 0.390625),
    x720(0.46875, 0.46875),
    x768(0.5, 0.5),
    x800(0.52083, 0.520833),
    x1080(0.703125, 0.703125),
    NO_SCALING(1, 1);

    private final double scaleX, scaleY;

    private EXOAnimationScreenConfig(final double scaleX, final double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public double scaleX(final double x) {
        return x * scaleX / x768.scaleX;
    }

    public double scaleY(final double y) {
        return y * scaleY / x768.scaleY;
    }
}

class EXOAnimationElement implements AnimStateGetter {
    enum ElementType {
        pure,
        spline,
        wobble,
        rotate,
        repeat,
        sequence,
        wiggle
    }

    double startTime;
    double duration;
    ElementType elementType;
    EXOAnimationCurveGetter fadeCurve;

    EXOAnimationElement() {
        elementType = ElementType.pure;
        startTime = 0.0;
        duration = 0.0;
    }

    public EXOAnimationElement appendCurve(EXOAnimationCurveGetter curve) {
        fadeCurve = curve;
        return this;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        return EXOAnimationState.identity();
    }

    public EXOAnimationState getStateForGlobalTime(double time, EXOImageView image) {
        if (time >= startTime && time < startTime + duration)
            return stateAtTime(time - startTime, image).fadeCurve((time - startTime) / duration, fadeCurve);
        return null;
    }
}

class EXOAnimationElementSpline extends EXOAnimationElement implements Spline.Callback {
    EXOAnimationElementSpline() {
        elementType = ElementType.spline;
    }

    static EXOAnimationElementSpline create(double startTime, double endTime, List<PointF> points) {
        EXOAnimationElementSpline ret = new EXOAnimationElementSpline();
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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

class EXOAnimationElementRotate extends EXOAnimationElement {
    double repeats = 1.0;

    EXOAnimationElementRotate() {
        elementType = ElementType.rotate;
    }

    static EXOAnimationElementRotate create(double startTime, double endTime, double repeats) {
        EXOAnimationElementRotate ret = new EXOAnimationElementRotate();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.repeats = repeats;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.rotation = time * repeats / duration * 360.0;
        return ret;
    }
}

class EXOAnimationElementWiggle extends EXOAnimationElement {

    double angleDelta;

    EXOAnimationElementWiggle() {
        elementType = ElementType.wiggle;
    }

    static EXOAnimationElementWiggle create(double startTime, double endTime, double angleDelta) {
        EXOAnimationElementWiggle ret = new EXOAnimationElementWiggle();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.angleDelta = angleDelta;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.rotation = Math.sin(time / duration * 2.0 * Math.PI) * angleDelta;
        return ret;
    }
}

class EXOAnimationElementRepeat extends EXOAnimationElement {
    double repeats = 1.0;
    EXOAnimationElement anim;

    EXOAnimationElementRepeat() {
        elementType = ElementType.repeat;
    }

    static EXOAnimationElementRepeat create(double repeats,EXOAnimationElement toRepeat) {
        EXOAnimationElementRepeat ret = new EXOAnimationElementRepeat();
        ret.repeats = repeats;
        ret.startTime = toRepeat.startTime;
        ret.duration = toRepeat.duration * repeats;
        ret.anim = toRepeat;
        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        return anim.stateAtTime(time % anim.duration, image);
    }
}

class EXOAnimationElementSequence extends EXOAnimationElement {

    ArrayList<EXOAnimationElement> elements = new ArrayList<EXOAnimationElement>();
    double currentEndPoint;

    EXOAnimationElementSequence() {
        elementType = ElementType.sequence;
    }

    static EXOAnimationElementSequence create() {
        EXOAnimationElementSequence ret = new EXOAnimationElementSequence();
        ret.currentEndPoint = 0.0;
        return ret;
    }

    void appendAnimation(EXOAnimationElement element)
    {
        element.startTime = currentEndPoint;
        currentEndPoint += element.duration;
        elements.add(element);
    }

    void putAnimation(EXOAnimationElement element)
    {
        elements.add(element);
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        for (int i = 0; i < elements.size(); ++i) {
            EXOAnimationElement element = elements.get(i);

            EXOAnimationState state = element.getStateForGlobalTime(time, image);
            if (state != null)
                ret.combine(state);
        }
        return ret;
    }
}

class EXOAnimationElementWobble extends EXOAnimationElement {
    double factor = 1.0;

    EXOAnimationElementWobble() {
        elementType = ElementType.wobble;
    }

    static EXOAnimationElementWobble create(double startTime, double endTime, double factor) {
        EXOAnimationElementWobble ret = new EXOAnimationElementWobble();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.factor = factor;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        double wobble = Math.sin(time * Math.PI * 2.0 / duration) * factor;
        double xScale = 1.0 + wobble;
        double yScale = 1.0 / xScale;

        EXOAnimationState ret = EXOAnimationState.identity();
        ret.scaleX = xScale;
        ret.scaleY = yScale;

        return ret;
    }
}

class EXOAnimationElementJump extends EXOAnimationElement {
    double height = 1.0;

    EXOAnimationElementJump() {
        elementType = ElementType.rotate;
    }

    static EXOAnimationElementJump create(double startTime, double endTime, double height) {
        EXOAnimationElementJump ret = new EXOAnimationElementJump();
        ret.startTime = startTime;
        ret.duration = endTime - startTime;
        ret.height = height;

        return ret;
    }

    @Override
    public EXOAnimationState stateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        ret.posY = -Math.sin(time / duration * Math.PI) * height;
        return ret;
    }
}

class EXOAnimationCollection {
    ArrayList<EXOAnimationElement> elements = new ArrayList<EXOAnimationElement>();
    public static EXOAnimationScreenConfig screen = EXOAnimationScreenConfig.NO_SCALING;
    double preDelay = 0.0;

    public static EXOAnimationCollection create() {
        return new EXOAnimationCollection();
    }

    public EXOAnimationCollection addAnimation(EXOAnimationElement element) {
        addElement(element);
        return this;
    }

    void addElement(EXOAnimationElement element) {
        elements.add(element);
    }

    EXOAnimationState getCombinedStateAtTime(double time, EXOImageView image) {
        EXOAnimationState ret = EXOAnimationState.identity();
        for (int i = 0; i < elements.size(); ++i) {
            EXOAnimationElement element = elements.get(i);

            EXOAnimationState state = element.getStateForGlobalTime(time, image);
            if (state != null)
                ret.combine(state);
        }
        return ret;
    }

    void finalizeState(EXOAnimationState state, EXOImageView image) {
        //state.posX += state.scaleX * (1.0 - image.getWidth()) * 0.5;
        //state.posY += state.scaleY * (1.0 - image.getHeight()) * 0.5;

        state.posX = screen.scaleX(state.posX);
        state.posY = screen.scaleY(state.posY);
    }

    Animation generateAnimationFromTimeToTime(double time1, double time2, EXOImageView image, double preDelay) {
        EXOAnimationState state1 = getCombinedStateAtTime(time1, image);
        EXOAnimationState state2 = getCombinedStateAtTime(time2, image);

        finalizeState(state1, image);
        finalizeState(state2, image);

        Animation scaleAnimation = new ScaleAnimation((float) state1.scaleX, (float) state2.scaleX, (float) state1.scaleY, (float) state2.scaleY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        Animation moveAnimation = new TranslateAnimation((float) state1.posX, (float) state2.posX, (float) state1.posY, (float) state2.posY);
        Animation rotateAnimation = new RotateAnimation((float) state1.rotation, (float) state2.rotation, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration((long) ((time2 - time1) * 1000.0));
        animationSet.setStartOffset((long) (preDelay * 1000.0));
        animationSet.setInterpolator(new LinearInterpolator());

        if (!(eps(state1.scaleX, 1.0) && eps(state2.scaleX, 1.0) && eps(state1.scaleY, 1.0) && eps(state2.scaleY, 1.0)))
            animationSet.addAnimation(scaleAnimation);

        if (!(eps(state1.rotation, 0.0) && eps(state2.rotation, 0.0)))
            animationSet.addAnimation(rotateAnimation);

        if (!(eps(state1.posX, 0.0) && eps(state2.posX, 0.0) && eps(state1.posY, 0.0) && eps(state2.posY, 0.0)))
            animationSet.addAnimation(moveAnimation);

        animationSet.setFillAfter(true);
        animationSet.setFillBefore(true);
        animationSet.setFillEnabled(true);
        return animationSet;
    }

    private boolean eps(final double val1, final double val2) {
        return Math.abs(val2 - val1) < 0.01;
    }

    ArrayList<Animation> generateWholeAnimation(double timeDelta, EXOImageView image) {

        double startTime = 0.0;
        double endTime = 0.0;

        for (int i = 0; i < elements.size(); ++i) {
            EXOAnimationElement element = elements.get(i);
            double endHere = element.startTime + element.duration;

            if (endHere > endTime) endTime = endHere;
        }

        ArrayList<Animation> ret = new ArrayList<Animation>();
        boolean first = true;
        for (double time = startTime; time < endTime; time += timeDelta) {
            Animation anim = generateAnimationFromTimeToTime(time, time + timeDelta, image, first ? this.preDelay : 0.0);
            ret.add(anim);
            first = false;
        }

        return ret;
    }

    EXOAnimationCollection waitBefore(double time)
    {
        this.preDelay = time;
        return this;
    }
}

class EXOAnimationQueue implements Animation.AnimationListener {

    ArrayList<Animation> animations = new ArrayList<Animation>();
    int currentIndex;
    boolean looping = false;
    EXOImageView viewToRunOn;
    Animation currentActive;

    void generateWithCollection(EXOAnimationCollection collection, EXOImageView toStartOn, double fps) {
        viewToRunOn = toStartOn;
        viewToRunOn.dispatchDisplayHint();
        animations = collection.generateWholeAnimation(1.0 / fps, viewToRunOn);
    }

    void run() {
        currentIndex = -1;
        next();
    }

    boolean next() {
        currentIndex++;
        if (currentIndex == animations.size()) {
            if (looping) {
                run();
                return true;
            }
            return false;
        }

        if (currentActive != null)
            currentActive.setAnimationListener(null);
        currentActive = animations.get(currentIndex);
        if (currentActive != null) {
            currentActive.setAnimationListener(this);
            viewToRunOn.clearAnimation();
            viewToRunOn.startAnimation(currentActive);
        } else
            return next();
        return true;
    }

    @Override
    public void onAnimationStart(final Animation animation) {
    }

    @Override
    public void onAnimationEnd(final Animation animation) {
        if (currentActive == animation)
            next();
    }

    @Override
    public void onAnimationRepeat(final Animation animation) {
    }
}
