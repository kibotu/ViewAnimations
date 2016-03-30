package net.kibotu.android.view.animations.exo;

public interface IAnimStateGetterGlobal {

    public EXOAnimationState getStateForGlobalTime(double time, EXOImageView image);

    public double getDuration();
}