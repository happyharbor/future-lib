package io.happyharbor.genericlib;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FutureCollectors {

    private FutureCollectors() {
    }

    public static <T> Collector<CompletableFuture<T>, ?, CompletableFuture<List<T>>> sequenceCollector() {
        return Collectors.collectingAndThen(Collectors.toList(), com ->
                CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[0]))
                        .thenApply(v -> com.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList())
                        ));
    }

    public static <T> Collector<CompletableFuture<T>, ?, CompletableFuture<Stream<T>>> sequenceStreamCollector() {
        return Collectors.collectingAndThen(Collectors.toList(), com ->
                CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[0]))
                        .thenApply(v -> com.stream()
                                .map(CompletableFuture::join)
                        ));
    }

    public static <T> Collector<CompletableFuture<T>, ?, CompletableFuture<List<T>>> sequenceFailOnFirstErrorCollector() {
        return Collectors.collectingAndThen(Collectors.toList(), com -> {
            CompletableFuture<List<T>> result = CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[0]))
                    .thenApply(v -> com.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList())
                    );

            com.forEach(f -> f.whenComplete((t, ex) -> {
                if (ex != null) {
                    result.completeExceptionally(ex);
                }
            }));

            return result;
        });
    }
}
