package com.manifestors.shinrai.client.utils.math

class TimingUtil {
    private var startTime: Long

    init {
        startTime = System.currentTimeMillis()
    }

    val elapsedTime: Long
        get() = System.currentTimeMillis() - startTime

    fun hasElapsed(milliseconds: Long): Boolean {
        return this.elapsedTime >= milliseconds
    }

    fun reset() {
        this.startTime = System.currentTimeMillis()
    }
}
