package com.amigoscode;

import com.amigoscode.entity.Customer;
import com.amigoscode.repository.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;
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
            Customer customers = new Customer(
                    name.firstName() + " " + name.lastName(),
                    name.firstName().toLowerCase() + "."+ name.lastName().toLowerCase() + "@amigoscode.com",
                    random.nextInt(16, 99));

            Customer jamila = new Customer(
                    "Jamila",
                    "jamila@gmail.com",
                    19);
//            List<Customer> customers = List.of(alex, jamila);
            customerRepository.save(customers);
        };
    }

}
