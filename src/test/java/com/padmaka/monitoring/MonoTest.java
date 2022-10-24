package com.padmaka.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoTest {

    @Test
    void firstMono() {
        Mono.just("John Doe")
                .log();
    }

    @Test
    void monoWithConsumer() {
        Mono.just("John Doe")
                .log()
                .subscribe(System.out::println);
    }

    @Test
    void monoWithDoOn() {
        Mono.just("John Doe").log()
                .doOnSubscribe(subscription -> System.out.println("Subscribed: " + subscription))
                .doOnRequest(request -> System.out.println("Request: " + request))
                .doOnSuccess(complete -> System.out.println("Complete: " + complete))
                .subscribe(System.out::println);
    }

    @Test
    void emptyMono() {
        Mono.empty()
                .log()
                .subscribe(System.out::println);
    }

    @Test
    void emptyCompleteConsumerMono() {
        Mono.empty()
                .log()
                .subscribe(System.out::println,
                        null,
                        () -> System.out.println("Done")
                );
    }

    @Test
    void errorRuntimeExceptionMono() {
        Mono.error(new RuntimeException())
                .log()
                .subscribe();
    }

    @Test
    void errorExceptionMono() {
        Mono.error(new Exception())
                .log()
                .subscribe();
    }

    @Test
    void errorOnConsumerMono() {
        Mono.error(new Exception())
                .log()
                .subscribe(System.out::println, e -> System.out.println("Error: " + e));
    }

    @Test
    void errorOnErrorResumeMono() {
        Mono.error(new Exception())
                .onErrorResume(e -> {
                    System.out.println("Caught: " + e);
                    return Mono.just("B");
                })
                .log()
                .subscribe();
    }

    @Test
    void errorOnErrorReturnMono() {
        Mono.error(new Exception())
                .onErrorReturn("B")
                .log()
                .subscribe();
    }
}
