package com.exozet.exoanimations;

public interface IAnimStateGetterGlobal {

    public EXOAnimationState getStateForGlobalTime(double time, EXOImageView image);

    public double getDuration();
}