package com.duing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.duing.mapper")
@SpringBootApplication
@EnableScheduling
public class EpidemicApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpidemicApplication.class, args);
    }

}
