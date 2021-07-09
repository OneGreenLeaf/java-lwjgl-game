package main.until;

public class Time {
    public static  float tmeStarted = System.nanoTime();

    public  static float getTime(){
        return (float) ((System.nanoTime()- tmeStarted) * 1E-9); //convert nanosecond to second
    }
}
