package com.amigoscode.entity;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {

}
