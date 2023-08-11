package controllers;

import data.Jdbc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class StudentGradingSystemSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentGradingSystemSpringApplication.class, args);
    }

}
