package com.hitman.musicx.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThread {
    public static final int NUMBER_OF_THREAD=4;
    public static final ExecutorService executor= Executors.newFixedThreadPool(NUMBER_OF_THREAD);
}
