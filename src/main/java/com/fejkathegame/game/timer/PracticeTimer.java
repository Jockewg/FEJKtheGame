package com.fejkathegame.game.timer;

/**
 * Created by Swartt on 2015-05-11.
 */
public class PracticeTimer {

    private int timerDuration;
    private boolean timerRunning = false;
    
    private int countDownLength;
   private boolean countdownRunning = false;
  private int end = 0;
  private int secondsPassed = 0;

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

   public void calculateSecond(int delta) {
      secondsPassed += delta;
      if (secondsPassed > 1000) {
         secondsPassed = 0;
         countDownLength--;
         if(timerRunning && !countdownRunning) {
         timerDuration++;
         }
      }
   }
   public int getCurrentCountdownTime() {
      if (countDownLength <= end ) {
         countDownLength = 0;
         stopCountdown();
      }
      return countDownLength;
   }

   public boolean isCountdownRunning() {
      return countdownRunning;
   }
}
