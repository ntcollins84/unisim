package io.github.uoyteamsix;

public class Timer {

    private float timeLeft;
    private boolean isPaused;

    public Timer (float timeLeft) {
        this.timeLeft = timeLeft;
        this.isPaused = false;
    }

    public void updateTime(float deltaTime) {
        if (!isPaused && timeLeft > 0) {
            timeLeft -= deltaTime;
        }
        if (timeLeft <= 0) {
            isPaused = true;
        }
    }

    public void pauseTime() { isPaused = true; }

    public void resumeTime() { isPaused = false; }

    public boolean isTimeEnded() {
        return timeLeft <= 0;
    }

    public float getTimeLeft() { return timeLeft; }
}
