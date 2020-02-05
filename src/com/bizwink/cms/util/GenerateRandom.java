package com.bizwink.cms.util;

import java.util.Random;

/**
 * Created by petersong on 16-3-11.
 */
public class GenerateRandom {
    public static int getRandomNum(int min,int max) {
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
}
