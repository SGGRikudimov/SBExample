package uk.co.sgene.sbexample;

import org.springframework.boot.SpringApplication;

public final class SBExampleApplication {

    public static void main(String[] args) {
        new SpringApplication(AppConfig.class).run(args);
    }
}
