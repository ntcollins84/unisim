package io.github.uoyteamsix;

/**
A class to track the time remaining in the game
 */
public class Timer {

    private float timeLeft;
    private boolean isPaused;

    public Timer (float timeLeft) {
        this.timeLeft = timeLeft;
        this.isPaused = true;
    }

    /**
     * Runs down timer as game progresses
     * @param deltaTime
     */
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

    /**
     *
     * @return whether game is paused or not
     */
    public boolean isPaused() { return isPaused; }

    /**
     *
     * @return whether game has ended
     */
    public boolean isTimeEnded() {
        return timeLeft <= 0;
    }

    /**
     *
     * @return amount of time left
     */
    public float getTimeLeft() { return timeLeft; }
}