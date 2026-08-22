package com.codeatlas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        System.out.println("JVM timezone: " + java.util.TimeZone.getDefault().getID());
        SpringApplication.run(Application.class, args);
	}

}
