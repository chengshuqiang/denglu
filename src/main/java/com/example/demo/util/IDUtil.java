package com.example.demo.util;

public class IDUtil {

    public static long generateId() {
        return System.currentTimeMillis() * 1000L + Math.round(Math.random() * 1000.0D);
    }
}
