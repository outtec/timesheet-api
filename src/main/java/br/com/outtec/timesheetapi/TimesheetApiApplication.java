package br.com.outtec.timesheetapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.enums.PerfilEnum;
import br.com.outtec.timesheetapi.repositories.CollaboratorRepository;
import br.com.outtec.timesheetapi.security.domain.User;
import br.com.outtec.timesheetapi.security.repositories.UserRepository;
import br.com.outtec.utils.PasswordUtils;


@SpringBootApplication
public class TimesheetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetApiApplication.class, args);
	}
	private static final String PASSWORD = "123456!";
	
	@Autowired
	private UserRepository usuarioRepository;
	

	@Autowired
	private CollaboratorRepository colaboratorRepository;
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
			//Criando usuário
			User usuario = new User();
			usuario.setEmail("joycesaquino@gmail.com");
			usuario.setPerfil(PerfilEnum.ROLE_USER);
			usuario.setSenha(PasswordUtils.getBCrypt(PASSWORD));
			this.usuarioRepository.save(usuario);
			System.out.println(usuario.toString());
			
			User admin = new User();
			admin.setEmail("gbvirtual@gmail.com");
			admin.setPerfil(PerfilEnum.ROLE_ADMIN);
			admin.setSenha(PasswordUtils.getBCrypt(PASSWORD));
			this.usuarioRepository.save(admin);
			System.out.println(admin);
			
			//Criando collaborator
			Collaborator colaborador = new Collaborator();
			//colaborador.setId(1L);
			colaborador.setName("Josenildo");
			colaborador.setPassword("123456!");
			colaborador.setPerfil(PerfilEnum.ROLE_USER);
			this.colaboratorRepository.save(colaborador);
			System.out.println("Colaborador cadastrado - Nome :" + colaborador.getName());
			
			//Criando uma entrada de timesheet
			
					
				
			//Testando Password com Bcrypt
			String encodedPassword = PasswordUtils.getBCrypt(PASSWORD);
			System.out.println("Password encoded : " + encodedPassword);
			
			encodedPassword = PasswordUtils.getBCrypt(PASSWORD);
			System.out.println("Password encoded hash again : " + encodedPassword);
			
			encodedPassword = PasswordUtils.getBCrypt(PASSWORD);
			System.out.println("Valid Password : " + PasswordUtils.isValidPassword(PASSWORD, encodedPassword));
			
			
		};
}
}
