package com.amigoscode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
public class HelloController {

    @GetMapping("/greet")
    public GreetResponse greet(@RequestParam(value = "name", required = false) String name) {
        String greetMessage = name == null || name.isBlank() ? "Hello" : "Hello " + name;
        GreetResponse response = new GreetResponse(
                greetMessage,
                List.of("Java", "GoLang", "JavaScript"),
                new Person("John", 28, 30_000));
        return response;
    }

    record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person) {}

    record Person(String name, int age, double savings) {}

}
