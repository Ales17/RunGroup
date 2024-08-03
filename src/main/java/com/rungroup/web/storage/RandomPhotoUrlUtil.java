package com.rungroup.web.storage;

import java.util.Random;

public class RandomPhotoUrlUtil {
    private static final int PHOTO_WIDTH = 800;
    private static final int PHOTO_HEIGHT = 600;
    private static final String SERVICE_URL = "https://picsum.photos/";
    private static final String RANDOM_PHOTO_URL = String.format("%s/%d/%d", SERVICE_URL, PHOTO_WIDTH, PHOTO_HEIGHT);

    private static int getRandomNumber() {
        Random random = new Random(100);
        return Math.abs(random.nextInt());
    }

    public static String getRandomPhotoUrl() {
        return String.format("%s?random=%d", RANDOM_PHOTO_URL, getRandomNumber());
    }
}
