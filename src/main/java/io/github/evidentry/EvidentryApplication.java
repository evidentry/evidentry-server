package io.github.evidentry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EvidentryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvidentryApplication.class, args);
    }

}
