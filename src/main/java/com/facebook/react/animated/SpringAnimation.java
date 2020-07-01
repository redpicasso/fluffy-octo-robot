package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;

class SpringAnimation extends AnimationDriver {
    private static final double MAX_DELTA_TIME_SEC = 0.064d;
    private static final double SOLVER_TIMESTEP_SEC = 0.001d;
    private int mCurrentLoop;
    private final PhysicsState mCurrentState = new PhysicsState();
    private double mDisplacementFromRestThreshold;
    private double mEndValue;
    private double mInitialVelocity;
    private int mIterations;
    private long mLastTime;
    private double mOriginalValue;
    private boolean mOvershootClampingEnabled;
    private double mRestSpeedThreshold;
    private double mSpringDamping;
    private double mSpringMass;
    private boolean mSpringStarted;
    private double mSpringStiffness;
    private double mStartValue;
    private double mTimeAccumulator;

    private static class PhysicsState {
        double position;
        double velocity;

        private PhysicsState() {
        }
    }

    SpringAnimation(ReadableMap readableMap) {
        this.mCurrentState.velocity = readableMap.getDouble("initialVelocity");
        resetConfig(readableMap);
    }

    public void resetConfig(ReadableMap readableMap) {
        this.mSpringStiffness = readableMap.getDouble("stiffness");
        this.mSpringDamping = readableMap.getDouble("damping");
        this.mSpringMass = readableMap.getDouble("mass");
        this.mInitialVelocity = this.mCurrentState.velocity;
        this.mEndValue = readableMap.getDouble("toValue");
        this.mRestSpeedThreshold = readableMap.getDouble("restSpeedThreshold");
        this.mDisplacementFromRestThreshold = readableMap.getDouble("restDisplacementThreshold");
        this.mOvershootClampingEnabled = readableMap.getBoolean("overshootClamping");
        String str = "iterations";
        boolean z = true;
        this.mIterations = readableMap.hasKey(str) ? readableMap.getInt(str) : 1;
        if (this.mIterations != 0) {
            z = false;
        }
        this.mHasFinished = z;
        this.mCurrentLoop = 0;
        this.mTimeAccumulator = 0.0d;
        this.mSpringStarted = false;
    }

    public void runAnimationStep(long j) {
        j /= 1000000;
        if (!this.mSpringStarted) {
            if (this.mCurrentLoop == 0) {
                this.mOriginalValue = this.mAnimatedValue.mValue;
                this.mCurrentLoop = 1;
            }
            PhysicsState physicsState = this.mCurrentState;
            double d = this.mAnimatedValue.mValue;
            physicsState.position = d;
            this.mStartValue = d;
            this.mLastTime = j;
            this.mTimeAccumulator = 0.0d;
            this.mSpringStarted = true;
        }
        advance(((double) (j - this.mLastTime)) / 1000.0d);
        this.mLastTime = j;
        this.mAnimatedValue.mValue = this.mCurrentState.position;
        if (isAtRest()) {
            int i = this.mIterations;
            if (i == -1 || this.mCurrentLoop < i) {
                this.mSpringStarted = false;
                this.mAnimatedValue.mValue = this.mOriginalValue;
                this.mCurrentLoop++;
                return;
            }
            this.mHasFinished = true;
        }
    }

    private double getDisplacementDistanceForState(PhysicsState physicsState) {
        return Math.abs(this.mEndValue - physicsState.position);
    }

    private boolean isAtRest() {
        return Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringStiffness == 0.0d);
    }

    private boolean isOvershooting() {
        return this.mSpringStiffness > 0.0d && ((this.mStartValue < this.mEndValue && this.mCurrentState.position > this.mEndValue) || (this.mStartValue > this.mEndValue && this.mCurrentState.position < this.mEndValue));
    }

    private void advance(double d) {
        if (!isAtRest()) {
            double d2 = MAX_DELTA_TIME_SEC;
            if (d <= MAX_DELTA_TIME_SEC) {
                d2 = d;
            }
            this.mTimeAccumulator += d2;
            d2 = this.mSpringDamping;
            double d3 = this.mSpringMass;
            double d4 = this.mSpringStiffness;
            double d5 = -this.mInitialVelocity;
            d2 /= Math.sqrt(d4 * d3) * 2.0d;
            d3 = Math.sqrt(d4 / d3);
            d4 = Math.sqrt(1.0d - (d2 * d2)) * d3;
            double d6 = this.mEndValue - this.mStartValue;
            double d7 = this.mTimeAccumulator;
            if (d2 < 1.0d) {
                d = Math.exp(((-d2) * d3) * d7);
                d2 *= d3;
                d5 += d2 * d6;
                d7 *= d4;
                d2 = ((d2 * d) * (((Math.sin(d7) * d5) / d4) + (Math.cos(d7) * d6))) - (((Math.cos(d7) * d5) - ((d4 * d6) * Math.sin(d7))) * d);
                d4 = this.mEndValue - ((((d5 / d4) * Math.sin(d7)) + (Math.cos(d7) * d6)) * d);
            } else {
                d2 = Math.exp((-d3) * d7);
                d4 = this.mEndValue - (((((d3 * d6) + d5) * d7) + d6) * d2);
                d2 *= (d5 * ((d7 * d3) - 1.0d)) + ((d7 * d6) * (d3 * d3));
            }
            PhysicsState physicsState = this.mCurrentState;
            physicsState.position = d4;
            physicsState.velocity = d2;
            if (isAtRest() || (this.mOvershootClampingEnabled && isOvershooting())) {
                if (this.mSpringStiffness > 0.0d) {
                    d2 = this.mEndValue;
                    this.mStartValue = d2;
                    this.mCurrentState.position = d2;
                } else {
                    this.mEndValue = this.mCurrentState.position;
                    this.mStartValue = this.mEndValue;
                }
                this.mCurrentState.velocity = 0.0d;
            }
        }
    }
}
