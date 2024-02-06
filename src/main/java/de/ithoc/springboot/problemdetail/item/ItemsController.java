package de.ithoc.springboot.problemdetail.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemsController {

    private final List<Item> items = List.of(
            new Item("Item 1", "Description for Item 1"),
            new Item("Item 2", "Description for Item 2"),
            new Item("Item 3", "Description for Item 3")
    );


    @GetMapping
    public ResponseEntity<List<Item>> getItems() {

        return ResponseEntity.ok(items);
    }

    /**
     * This method will throw an exception with its default message structure.
     */
    @GetMapping("/exception-basic/{name}")
    public ResponseEntity<Item> getItemExceptionBasic(@PathVariable String name) {

        Item foundItem = items.stream()
                .findFirst()
                .filter(item -> item.getName().equals(name))
                .orElseThrow(ItemNotFoundException::new);

        return ResponseEntity.ok(foundItem);
    }

    @GetMapping("/exception-advice/{name}")
    public ResponseEntity<String> getItemExceptionAdvice(@PathVariable String name) {

        Optional<Item> foundItem = items.stream()
                .findFirst()
                .filter(item -> item.getName().equals("Item 1"));

        if(foundItem.isPresent()) {
            throw new ItemAlreadyExistsException("Item '" + name + "' already available.");
        }

        return ResponseEntity.ok("No item available, yet.");
    }

}
