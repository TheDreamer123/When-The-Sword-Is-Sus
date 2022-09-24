package net.dreamer.wtsis.util;

import java.util.Random;

public class SecondsToTicksConverter {
    public static int secondsToTicks(int seconds) {
        return seconds * 20;
    }

    public static int secondsToTicks(int min, int max) {
        return new Random().nextInt(min,max) * 20;
    }
}
