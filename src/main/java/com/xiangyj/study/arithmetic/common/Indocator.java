package com.xiangyj.study.arithmetic.common;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 简单的计数器
 */
public class Indocator {
    private static final Indocator INSTANCE = new Indocator();

    private Indocator() {
    }

    private AtomicLong requestCount = new AtomicLong(0);

    private AtomicLong successCount = new AtomicLong(0);

    private AtomicLong failureCount = new AtomicLong(0);

    public static Indocator getInstance() {
        return INSTANCE;
    }

    public void newRequest() {
        requestCount.incrementAndGet();
    }

    public void newRequestProcessed() {
        successCount.incrementAndGet();
    }

    public void requestProcessedFailed() {
        failureCount.incrementAndGet();
    }

    public long getRequestCount() {
        return requestCount.get();
    }

    public long getSuccessCount() {
        return successCount.get();
    }

    public long getFailureCountCount() {
        return failureCount.get();
    }

    public void reset() {
        requestCount.set(0);
        successCount.set(0);
        failureCount.set(0);
    }

}
