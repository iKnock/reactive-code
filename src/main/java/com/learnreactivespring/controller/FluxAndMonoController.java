package com.learnreactivespring.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public void returnFlux() {

        Subscriber subscriber = new Subscriber<Long>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Long integer) {
                //  elements.add(integer);
                log.info("on Next event" + integer);
            }

            @Override
            public void onError(Throwable t) {
                log.info("on Error event");
            }

            @Override
            public void onComplete() {
                log.info("Hello on the complete event");
            }
        };

        Flux.interval(Duration.ofSeconds(1)).subscribe(subscriber);

    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> returnFluxStream() {
        return Flux.just(1, 2, 3, 4, 5, 6).interval(Duration.ofSeconds(1)).log();
    }

    @GetMapping("/mono")
    public Mono<Integer> returnMono() {

        return Mono.just(1)
                .log();

    }

}
