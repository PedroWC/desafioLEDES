package com.ledes.desafio.institute_manager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Institute Manager API", version = "1.0", description = "Gerenciamento de Instituições"))
public class InstituteManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstituteManagerApplication.class, args);
	}

}
