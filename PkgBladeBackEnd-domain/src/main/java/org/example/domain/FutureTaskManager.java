package org.example.domain;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;


@Component
public class FutureTaskManager {
    private static final ConcurrentHashMap<String, Future<?>> futureTaskMap = new ConcurrentHashMap<>();

    public static <T> void addTask(String missionName, Future<T> future) {
        futureTaskMap.put(missionName, future);
    }

    @SuppressWarnings("unchecked")
    public static <T> Future<T> getTaskFuture(String missionName, Class<T> type) {
        return (Future<T>) futureTaskMap.get(missionName);
    }

    public static void removeTask(String missionName) {
        futureTaskMap.remove(missionName);
    }
}
