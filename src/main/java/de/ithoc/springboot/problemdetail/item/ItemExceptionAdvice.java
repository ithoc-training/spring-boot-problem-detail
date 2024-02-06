package de.ithoc.springboot.problemdetail.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

/**
 * Example structure for a problem detail:
 * <p>
 * {
 *   "type": "http://localhost:18084/api/items/exception-advice/item-already-exists",
 *   "title": "Item already exists
 *   "status": 409,
 *   "detail": "Item with name 'Item 1' already exists.
 *   "instance": "/api/items/exception-advice
 * }
 * </p>
 */
@RestControllerAdvice
public class ItemExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ProblemDetail alreadyExists(RuntimeException e) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Item already exists");
        problemDetail.setType(URI.create("http://localhost:18084/api/items/exception-advice/item-already-exists"));
        problemDetail.setInstance(URI.create("/api/items/exception-advice"));

        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        problemDetail.setProperty("category", "Electronics");

        return problemDetail;
    }

}
