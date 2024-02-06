package de.ithoc.springboot.problemdetail.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemsControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldRespondProblemDetail() {

        String itemName = "Item 1";
        long timestamp = System.currentTimeMillis() - 1000;

        webTestClient.get()
                .uri("/items/exception-advice/" + itemName)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody(ProblemDetail.class)
                .value(problemDetail -> {
                    assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
                    assertEquals("Item already exists", problemDetail.getTitle());
                    assertEquals("Item 'Item 1' already available.", problemDetail.getDetail());
                    assertEquals(
                            URI.create("http://localhost:18084/api/items/exception-advice/item-already-exists"),
                            problemDetail.getType());
                    assertEquals(URI.create("/api/items/exception-advice"), problemDetail.getInstance());
                    assertTrue(timestamp < (Long) Objects.requireNonNull(
                            problemDetail.getProperties()).get("timestamp"));
                    assertEquals(
                            "Electronics",
                            Objects.requireNonNull(problemDetail.getProperties()).get("category")
                    );
                });
    }

}