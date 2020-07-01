package com.rnim.rn.audio;

public class StopWatch {
    private float elapsedTime = 0.0f;
    private boolean paused = true;
    private long start;

    public void start() {
        this.start = System.currentTimeMillis();
        this.paused = false;
    }

    public float stop() {
        if (!this.paused) {
            this.elapsedTime += ((float) (System.currentTimeMillis() - this.start)) / 1000.0f;
            this.paused = true;
        }
        return this.elapsedTime;
    }

    public void reset() {
        this.start = 0;
        this.elapsedTime = 0.0f;
        this.paused = true;
    }

    public float getTimeSeconds() {
        if (this.paused) {
            return this.elapsedTime;
        }
        long currentTimeMillis = System.currentTimeMillis();
        return (((float) (currentTimeMillis - this.start)) / 1000.0f) + this.elapsedTime;
    }
}
