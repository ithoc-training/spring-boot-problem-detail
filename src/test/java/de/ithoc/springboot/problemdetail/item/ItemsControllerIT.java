package de.ithoc.springboot.problemdetail.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemsControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void getItemExceptionAdvice() {

        String itemName = "Item 1";
        long timestamp = System.currentTimeMillis() - 1000;

        ResponseEntity<ProblemDetail> responseEntity =
                restTemplate.getForEntity("/items/exception-advice/" + itemName, ProblemDetail.class);

        if (responseEntity.getStatusCode().is4xxClientError()) {
            ProblemDetail problemDetail = (ProblemDetail) responseEntity.getBody();

            assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
            assert problemDetail != null;
            assertEquals("Item already exists", problemDetail.getTitle());
            assertEquals("Item 'Item 1' already available.", problemDetail.getDetail());
            assertEquals(
                    URI.create("http://localhost:18084/api/items/exception-advice/item-already-exists"),
                    problemDetail.getType());
            assertEquals(URI.create("/api/items/exception-advice"), problemDetail.getInstance());
            assertTrue(
                    timestamp < (Long) Objects.requireNonNull(problemDetail.getProperties()).get("timestamp")
            );
            assertEquals(
                    "Electronics",
                    Objects.requireNonNull(problemDetail.getProperties()).get("category")
            );
        } else {
            fail();
        }
    }

}