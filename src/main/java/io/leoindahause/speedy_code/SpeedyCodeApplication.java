package io.leoindahause.speedy_code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableJpaRepositories(basePackages = "io.leoindahause.repository")
@EntityScan(basePackages = "io.leoindahause.model")

@SpringBootApplication
public class SpeedyCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpeedyCodeApplication.class, args);
    }
}
