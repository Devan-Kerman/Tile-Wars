package util.testing;

import java.time.Clock;

public class StopWatch {
   private static long begin =0l;
   private static long end =0l;

   public static void start(){
        begin = Clock.systemDefaultZone().millis();
   }

   public static long stop(){
        end = Clock.systemDefaultZone().millis();
        return end-begin;
   }
}
