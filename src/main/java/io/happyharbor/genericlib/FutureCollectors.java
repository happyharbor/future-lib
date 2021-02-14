package io.happyharbor.genericlib;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public final class FutureCollectors {

    private FutureCollectors() {
    }

    /**
     * Collector that collects Completable Futures of T to aCompletable Futures of List<T>
     * @param <T> the payload of the Completable Future
     * @return a list of Completable Futures of T
     */
    public static <T> Collector<CompletableFuture<T>, ?, CompletableFuture<List<T>>> sequenceCollector() {
        return Collectors.collectingAndThen(Collectors.toList(), com ->
                CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[0]))
                        .thenApply(v -> com.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList())
                        ));
    }

    /**
     * Collector that collects Completable Futures of T to aCompletable Futures of Stream<T>
     * @param <T> the payload of the Completable Future
     * @return a Completable Future of Stream<T>
     */
    public static <T> Collector<CompletableFuture<T>, ?, CompletableFuture<Stream<T>>> sequenceStreamCollector() {
        return Collectors.collectingAndThen(Collectors.toList(), com ->
                CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[0]))
                        .thenApply(v -> com.stream()
                                .map(CompletableFuture::join)
                        ));
    }

    /**
     * Collector that collects Completable Futures of T to aCompletable Futures of List<T>.
     * The Completable Future will stop on the first error and complete Exceptionally. No new completable Futures will
     * start, but these that have already started will not be cancelled.
     * @param <T> the payload of the Completable Future
     * @return a list of Completable Futures of T
     */
    public static <T> Collector<CompletableFuture<T>, ?, CompletableFuture<List<T>>> sequenceFailOnFirstErrorCollector() {
        return Collectors.collectingAndThen(Collectors.toList(), com -> {
            CompletableFuture<List<T>> result = CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[0]))
                    .thenApply(v -> com.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList())
                    );

            com.forEach(f -> f.whenComplete((t, ex) -> {
                if (ex != null) {
                    log.info(ex.getMessage());
                    result.completeExceptionally(ex);
                }
            }));

            return result;
        });
    }
}
