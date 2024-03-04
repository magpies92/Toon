package Toon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ToonApplication {

	public static void main(String[] args) {
	    System.setProperty("file.encoding", "UTF-8");
	    SpringApplication.run(ToonApplication.class, args);
	}


}
