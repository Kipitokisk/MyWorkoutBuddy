package com.example.MyWorkoutBuddy;

import com.example.MyWorkoutBuddy.model.User;
import com.example.MyWorkoutBuddy.repository.ExerciseRepository;
import com.example.MyWorkoutBuddy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MyWorkoutBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWorkoutBuddyApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			User admin, user;
			if (userRepository.findByUsername("admin") == null) {
				admin = new User("admin", passwordEncoder.encode("123456"), "ROLE_ADMIN");
				userRepository.save(admin);
			}

			if (userRepository.findByUsername("user") == null) {
				user = new User("user", passwordEncoder.encode("123456"), "ROLE_USER");
				userRepository.save(user);
			}
		};
	}
}
