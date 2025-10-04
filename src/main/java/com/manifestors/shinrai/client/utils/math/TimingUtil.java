package com.manifestors.shinrai.client.utils.math;

public class TimingUtil {

    private long startTime;

    public TimingUtil() {
        startTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public boolean hasElapsed(long milliseconds) {
        return getElapsedTime() >= milliseconds;
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
    }

}
