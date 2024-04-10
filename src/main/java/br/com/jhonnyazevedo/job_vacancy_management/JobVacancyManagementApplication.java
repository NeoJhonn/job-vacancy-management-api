package br.com.jhonnyazevedo.job_vacancy_management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Congigurações do Swagger, documentação
@OpenAPIDefinition(
		info = @Info(
			title = "Gestão de Vagas",
			description = "API responsável pela gestão de vagas",
			version = "1.0"
		)
)
public class JobVacancyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobVacancyManagementApplication.class, args);
	}

}
