package de.ithoc.springboot.problemdetail.item;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemAlreadyExistsException extends RuntimeException {

    public ItemAlreadyExistsException(String message) {
        super(message);
    }

}
