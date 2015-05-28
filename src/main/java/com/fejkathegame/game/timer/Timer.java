package com.fejkathegame.game.timer;

/**
 * Created by Swartt on 2015-05-11.
 */
public class Timer {

   private int timerDuration;
   private boolean timerRunning = false;

   private int countDownLength;
   private boolean countdownRunning = false;
   private int end = 0;
   private int secondsPassed = 0;
   private float reverse = 0;

   public void startTimer() {
      timerRunning = true;
   }

   public void stopTimer() {
      timerRunning = false;
   }

   public int getTimerDuration() {
      return timerDuration;
   }

   public void startCountdown(int length) {
      this.countDownLength = length;
      countdownRunning = true;
   }

   private void stopCountdown() {
      countdownRunning = false;
   }

    /**
     * resets the timer
     */
    public void resetTimer(){
        timerDuration = 0;
        secondsPassed = 0;
        reverse = 0;
        end = 0;
        timerRunning = false;
        countdownRunning = false;
    }

    /**
     * Calculates when a second has passed
     * @param delta
     */
   public void calculateSecond(int delta) {
       secondsPassed += delta;
       if (secondsPassed > 1000) {
           secondsPassed = 0;
           countDownLength--;
           if (timerRunning && !countdownRunning) {
               timerDuration++;
           }
       }
   }

    /**
     * @return the current value of the countdown
     */
   public int getCurrentCountdownTime() {
      if (countDownLength <= end) {
         countDownLength = 0;
         stopCountdown();
      }
      return countDownLength;
   }

    /**
     * @return the current value of the countdown in reverse order
     */
   public float getCurrentCountdownTimeInReverseIncrement() {
      int current = getCurrentCountdownTime();
      if (current > 0) {
         reverse += 0.045f;
      }
      return reverse;
   }

   public boolean isCountdownRunning() {
      return countdownRunning;
   }
}
