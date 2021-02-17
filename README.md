# Future Library
[![CircleCI](https://circleci.com/gh/happyharbor/future-lib.svg?style=svg)](https://circleci.com/gh/circleci/circleci-docs)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.happyharbor/future-library/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.happyharbor/future-lib)

A Completable Future library for java that help you make tedious transformations and error handling easier

## Future Collectors
A collection of collectors that help write functional code.

### List<CompletableFuture<T> to CompletableFuture<List<T>>
When dealing with Completable Futures there are many times that you will arrive at a point that you have a list of 
completable futures, and you would like to continue creating executions paths with the help of thenApply etc. 

Unfortunately this is not possible with a list, it would have to be a completable future list and that is what this set
of collectors is doing. There three alternatives:
- sequenceCollector: The most basic usage that will collect your Stream of CompletableFuture<T> to a List<CompletableFuture<T>. 
  Upon error, the error will be logged, and the future will complete exceptionally.
- sequenceStreamCollector: Same as the previous, but it will return a Stream<CompletableFuture<T>
- sequenceNoFailCollector: Unlike the previous two, this collector will log the error, but it will finish normally. 