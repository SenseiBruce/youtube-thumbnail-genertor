package com.thumbnailgen.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ThumbnailMetrics {

    public static final String FALLBACK_COUNTER = "thumbnail.ai.fallback.count";

    private final Counter fallbackCounter;

    public ThumbnailMetrics(MeterRegistry meterRegistry) {
        this.fallbackCounter = Counter.builder(FALLBACK_COUNTER)
                .description("Count of AI style/placement fallbacks")
                .register(meterRegistry);
    }

    public void recordFallback() {
        fallbackCounter.increment();
    }

    public double fallbackCount() {
        return fallbackCounter.count();
    }
}
