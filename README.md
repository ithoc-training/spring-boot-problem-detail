# Spring Boot Problem Detail

## Introduction
Spring Boot provide a feature to handle exceptions and convert their information to standard field in error structures.
It's called `Problem Detail` and available since Spring Boot 3. 

## Testing
```shell
curl --location 'http://localhost:18084/api/items/exception-advice/Item 1'
```
The error response looks like this:
```json
{
    "type": "http://localhost:18084/api/items/exception-advice/item-already-exists",
    "title": "Item already exists",
    "status": 409,
    "detail": "Item 'Item 1' already available.",
    "instance": "/api/items/exception-advice",
    "timestamp": 1707229208940,
    "category": "Electronics"
}
```
