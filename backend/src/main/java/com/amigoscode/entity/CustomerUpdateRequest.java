package com.amigoscode.entity;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {

}
