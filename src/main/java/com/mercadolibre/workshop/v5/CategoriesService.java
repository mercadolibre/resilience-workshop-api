package com.mercadolibre.workshop.v5;

import com.linecorp.armeria.client.HttpClient;
import com.linecorp.armeria.client.HttpClientBuilder;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreaker;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerBuilder;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerHttpClientBuilder;
import com.linecorp.armeria.client.circuitbreaker.CircuitBreakerStrategy;
import com.linecorp.armeria.common.HttpMethod;
import com.linecorp.armeria.common.HttpRequest;
import com.mercadolibre.workshop.models.Category;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

class CategoriesService {
    private final CircuitBreaker circuitBreaker = new CircuitBreakerBuilder()
            .failureRateThreshold(0.5)
            .minimumRequestThreshold(10)
            .trialRequestInterval(Duration.ofSeconds(3))
            .circuitOpenWindow(Duration.ofSeconds(10))
            .build();

    private final HttpClient client = new HttpClientBuilder("http://localhost:80/")
            .responseTimeout(Duration.ofSeconds(1))
            .decorator(
                    new CircuitBreakerHttpClientBuilder(CircuitBreakerStrategy.onServerErrorStatus())
                            .circuitBreakerMapping((ctx, req) -> circuitBreaker)
                            .newDecorator()
            )
            .build();

    private CategoriesService() {
    }

    static CategoriesService getInstance() {
        return Holder.INSTANCE;
    }

    CompletableFuture<Category> getCategory(String id) {
        return client
                .execute(HttpRequest.of(HttpMethod.GET, "/categories/" + id))
                .aggregate()
                .thenApply(response -> {
                    try {
                        return Utils.MAPPER.readValue(response.contentUtf8(), Category.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static class Holder {
        static final CategoriesService INSTANCE = new CategoriesService();
    }
}
