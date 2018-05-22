package br.com.outtec.timesheetapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
/**
 * 
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spring.web.plugins.*;
*/
@Configuration
@Profile("dev")
//@EnableSwagger2
public class SwaggerConfig {
	
/**
 * 
	
	@Bean	
	public <Docket> Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.outtec.timesheetapi.controllers"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Timesheet API")
				.description("Documentação da API de acesso aos endpoints do Timesheet.").version("1.0")
				.build();
	}
	 * @return
 */
}

