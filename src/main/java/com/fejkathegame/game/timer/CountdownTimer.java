package com.fejkathegame.game.timer;

/**
 *
 * @author Swartt
 */
public class CountdownTimer {

   private int length;
   private boolean running = false;
  private int end = 0;
  private int elapsed = 0;

   public CountdownTimer(int seconds) {
      length = seconds;
      
   }

   public void startTimer() {
      running = true;
   }

   private void stopTimer() {
      running = false;
   }

   public void calculateSecond(int delta) {
      elapsed += delta;
      if (elapsed > 1000) {
         elapsed = 0;
         length--;
      }
   }
   public int getCurrentTime() {
      if (length <= end ) {
         length = 0;
         stopTimer();
      }
      return length;
   }

   public boolean isRunning() {
      return running;
   }
   

}
