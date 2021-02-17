package com.liner.twod_exp.engine.animator;

public interface AnimationListener {
    void onStart();
    void onStop();
    void onEnd();
    void onAnimate(Animator animator);
}
