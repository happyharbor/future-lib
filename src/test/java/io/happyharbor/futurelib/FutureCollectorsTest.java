package io.happyharbor.futurelib;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FutureCollectorsTest {

    @Test
    void sequenceCollector_healthyFutures_completedNormally() {
        final CompletableFuture<List<String>> futureList = Stream.of(healthyFuture(), healthyFuture())
                .collect(FutureCollectors.sequenceCollector());
        final List<String> strings = futureList.join();
        assertEquals(2, strings.size());
    }

    @Test
    void sequenceCollector_unhealthyFutures_completedExceptionally() {
        final CompletableFuture<List<String>> futureList = Stream.of(healthyFuture(), unhealthyFuture())
                .collect(FutureCollectors.sequenceCollector());
        assertThrows(RuntimeException.class, futureList::join);
    }

    @Test
    void sequenceStreamCollector_healthyFutures_completedNormally() {
        final CompletableFuture<Stream<String>> futureList = Stream.of(healthyFuture(), healthyFuture())
                .collect(FutureCollectors.sequenceStreamCollector());
        final Stream<String> stream = futureList.join();
        final List<String> strings = stream.collect(Collectors.toList());
        assertEquals(2, strings.size());
    }

    @Test
    void sequenceStreamCollector_unhealthyFutures_completedExceptionally() {
        final CompletableFuture<Stream<String>> futureList = Stream.of(healthyFuture(), unhealthyFuture())
                .collect(FutureCollectors.sequenceStreamCollector());
        assertThrows(RuntimeException.class, futureList::join);
    }

    @Test
    void sequenceNoFailCollector_healthyFutures_completedNormally() {
        final CompletableFuture<List<String>> futureList = Stream.of(healthyFuture(), healthyFuture())
                .collect(FutureCollectors.sequenceNoFailCollector());
        final List<String> strings = futureList.join();
        assertEquals(2, strings.size());
    }

    @Test
    void sequenceNoFailCollector_unhealthyFutures_completedNormally() {
        final CompletableFuture<String> completedFuture = healthyFuture();
        final CompletableFuture<List<String>> futureList = Stream.of(completedFuture, unhealthyFuture())
                .collect(FutureCollectors.sequenceNoFailCollector());
        final List<String> strings = futureList.join();
        assertEquals(1, strings.size());
    }

    private CompletableFuture<String> unhealthyFuture() {
        final CompletableFuture<String> unhealthyFuture = new CompletableFuture<>();
        unhealthyFuture.completeExceptionally(new RuntimeException());
        return unhealthyFuture;
    }

    private CompletableFuture<String> healthyFuture() {
        final CompletableFuture<String> healthyFuture = new CompletableFuture<>();
        healthyFuture.complete("");
        return healthyFuture;
    }
}