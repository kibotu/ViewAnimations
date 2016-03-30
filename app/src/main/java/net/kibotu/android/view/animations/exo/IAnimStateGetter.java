package net.kibotu.android.view.animations.exo;

public interface IAnimStateGetter {
    public EXOAnimationState stateAtTime(double time, EXOImageView image);
}