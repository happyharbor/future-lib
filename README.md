# Future Library
[![CircleCI](https://circleci.com/gh/happyharbor/future-lib.svg?style=shield)](https://circleci.com/gh/circleci/circleci-docs)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.happyharbor/future-library/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.happyharbor/future-lib)
[![Coverage Status](https://coveralls.io/repos/github/happyharbor/future-lib/badge.svg)](https://coveralls.io/github/happyharbor/future-lib)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=happyharbor_future-lib&metric=alert_status)](https://sonarcloud.io/dashboard?id=happyharbor_future-lib)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=happyharbor_future-lib&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=happyharbor_future-lib)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=happyharbor_future-lib&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=happyharbor_future-lib)
[![GitHub license](https://img.shields.io/github/license/happyharbor/future-lib)](https://github.com/happyharbor/future-lib/blob/master/LICENCE)

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

## License
This project is licensed under [MIT license](https://www.mit.edu/~amini/LICENSE.md).