package org.nott;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.nott.generate"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}