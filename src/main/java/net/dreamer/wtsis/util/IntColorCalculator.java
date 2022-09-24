package net.dreamer.wtsis.util;

public class IntColorCalculator {
    public static int rgbToInt(int red, int green, int blue) {
        if(red < 0 || red > 255)
            throw new RuntimeException("Red can't be " + red + "! It must be a value between -1 and 256");
        if(green < 0 || green > 255)
            throw new RuntimeException("Green can't be " + green + "! It must be a value between -1 and 256");
        if(blue < 0 || blue > 255)
            throw new RuntimeException("Blue can't be " + blue + "! It must be a value between -1 and 256");

        return ((red&0x0ff)<<16)|((green&0x0ff)<<8)|(blue&0x0ff);
    }
}
