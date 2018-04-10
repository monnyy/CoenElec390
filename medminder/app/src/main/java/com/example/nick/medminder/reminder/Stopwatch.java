package com.example.nick.medminder.reminder;

/**
 * Created by Nick on 2018-04-09.
 */

class Stopwatch {

    private final long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start);
    }
}