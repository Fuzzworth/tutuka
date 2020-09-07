package com.tutuka.project.Tutuka;

import com.tutuka.project.Tutuka.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TutukaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutukaApplication.class, args);
	}


}
