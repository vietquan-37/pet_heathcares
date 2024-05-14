package vietquan37.com.example.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import vietquan37.com.example.projects.creation.AdminCreation;
import vietquan37.com.example.projects.repository.UserRepository;

@SpringBootApplication
public class ProjectsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ProjectsApplication.class, args);
		AdminCreation adminAccountCreator = context.getBean(AdminCreation.class);
		adminAccountCreator.createAdminAccount();
	}

	@Bean
	public AdminCreation adminAccountCreator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return new AdminCreation(userRepository, passwordEncoder);
	}
}