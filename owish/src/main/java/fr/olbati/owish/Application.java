package fr.olbati.owish;

import fr.olbati.owish.bean.UserWriteBean;
import fr.olbati.owish.entity.Role;
import fr.olbati.owish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing
public class Application implements CommandLineRunner{

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... params) throws Exception {
		UserWriteBean admin = new UserWriteBean();
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setFirstName("John");
		admin.setLastName("Doe");
		admin.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_ADMIN)));

		userService.signup(admin);

		UserWriteBean client = new UserWriteBean();
		client.setUsername("client");
		client.setPassword("client");
		client.setFirstName("Jane");
		client.setLastName("Doe");
		client.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_CLIENT)));

		userService.signup(client);
	}
}
