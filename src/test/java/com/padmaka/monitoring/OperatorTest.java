package com.padmaka.monitoring;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


public class OperatorTest {

    @Test
    void map() {
        Flux.range(1, 5)
                .map(i -> i * 10)
                .subscribe(System.out::println);
    }

    @Test
    void flatmap() {
        Flux.range(1, 5)
                .flatMap(i -> Flux.range(i * 10, 2))
                .subscribe(System.out::println);
    }

    @Test
    void flatMapMany() {
        Mono.just(3)
                .flatMapMany(i -> Flux.range(1, i))
                .subscribe(System.out::println);
    }

    @Test
    void concat() throws InterruptedException {
        Flux<Integer> oneToFile = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));

        Flux.concat(oneToFile, sixToTen)
                .subscribe(System.out::println);

//        oneToFile.concatWith(sixToTen)
//                .subscribe(System.out::println);

        Thread.sleep(4000);
    }

    @Test
    void merge() throws InterruptedException {
        Flux<Integer> oneToFile = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));

        Flux.merge(oneToFile, sixToTen)
                .subscribe(System.out::println);

//        oneToFile.mergeWith(sixToTen)
//                .subscribe(System.out::println);

        Thread.sleep(4000);
    }

    @Test
    void zip() throws InterruptedException {
        Flux<Integer> oneToFile = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));

        Flux.zip(oneToFile, sixToTen, (item1, item2) -> item1 + ", " + item2)
                .subscribe(System.out::println);

//        oneToFile.zipWith(sixToTen, (item1, item2) -> item1 + ", " + item2)
//                .subscribe(System.out::println);

        Thread.sleep(4000);
    }
}
