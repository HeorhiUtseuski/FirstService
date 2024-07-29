package by.gvu.firstservice.improov.rest;

import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class RestClientLoggingTimerHelper {
    @Setter
    private Long remoteRequestTimeStart;
    @Setter
    private Long remoteRequestTimeEnd;
    private Long localStartTime;
    private Long localEndTime;

    public void start() {
        this.localStartTime = System.nanoTime();
    }

    public void stop() {
        this.localEndTime = System.nanoTime();
    }

    public void submit(Consumer<Map<String, Long>> consumer) {
        if (localEndTime == null) {
            localEndTime = System.nanoTime();
        }

        consumer.accept(calculate());
    }

    public Map<String, Long> calculate() {
        Map<String, Long> timeoutStatistic = new ConcurrentHashMap<>();

        calculateDurationIfPresent(localStartTime, localEndTime, duration -> timeoutStatistic.put("localDuration", duration));
        calculateDurationIfPresent(remoteRequestTimeStart, remoteRequestTimeEnd, duration -> timeoutStatistic.put("remoteDuration", duration));
        calculateDurationIfPresent(timeoutStatistic.get("remoteDuration"), timeoutStatistic.get("localDuration"), duration -> timeoutStatistic.put("totalLatency", duration));

        return timeoutStatistic;
    }

    private void calculateDurationIfPresent(Long startTime, Long endTime, Consumer<Long> consumer) {
        if (startTime != null && endTime != null) {
            consumer.accept(endTime - startTime);
        }
    }
}