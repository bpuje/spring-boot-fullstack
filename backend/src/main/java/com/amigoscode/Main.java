package com.amigoscode;

import com.amigoscode.entity.Customer;
import com.amigoscode.entity.Gender;
import com.amigoscode.repository.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);


    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            var faker = new Faker();
            Random random = new Random();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            int age = random.nextInt(16, 99);
            Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
            Customer customers = new Customer(
                    firstName + " " + lastName,
                    firstName.toLowerCase() + "."+ lastName.toLowerCase() + "@amigoscode.com",
                    age,
                    gender);

            Customer jamila = new Customer(
                    "Jamila",
                    "jamila@gmail.com",
                    19, Gender.MALE);
//            List<Customer> customers = List.of(alex, jamila);
            customerRepository.save(customers);
        };
    }

}
