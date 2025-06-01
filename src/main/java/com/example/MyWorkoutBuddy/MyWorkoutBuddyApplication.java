package com.example.MyWorkoutBuddy;

import com.example.MyWorkoutBuddy.model.Exercise;
import com.example.MyWorkoutBuddy.model.User;
import com.example.MyWorkoutBuddy.repository.ExerciseRepository;
import com.example.MyWorkoutBuddy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class MyWorkoutBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWorkoutBuddyApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ExerciseRepository exerciseRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			User admin = new User("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN");
			User user = new User("user", passwordEncoder.encode("user123"), "ROLE_USER");
			userRepository.save(admin);
			userRepository.save(user);

			exerciseRepository.save(new Exercise(
					"Push-up",
					"Medium",
					3,
					15,
					Arrays.asList("Chest", "Triceps", "Shoulders"),
					"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQM1pa4ITVQ3ZKXTM807-Juor1IP9kv5DwmJw&s",
					false,
					user
			));
			exerciseRepository.save(new Exercise(
					"Squat",
					"Easy",
					4,
					20,
					Arrays.asList("Quadriceps", "Glutes", "Hamstrings"),
					"https://as2.ftcdn.net/v2/jpg/03/44/75/77/1000_F_344757785_UYxWLYhTqI1igC5T5d47d1uzVahVRh4m.jpg",
					false,
					user
			));
		};
	}
}
