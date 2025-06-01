package com.example.MyWorkoutBuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "exercises")
@Data
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private int sets;

    @Column(nullable = false)
    private int reps;

    @ElementCollection
    @CollectionTable(name = "exercise_targets", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "target")
    private List<String> targets;

    @Column(length = 10485760)
    private String image;

    private boolean favorite;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Exercise(String name, String difficulty, int sets, int reps, List<String> targets, String image, boolean favorite, User user) {
        this.name = name;
        this.difficulty = difficulty;
        this.sets = sets;
        this.reps = reps;
        this.targets = targets;
        this.image = image;
        this.favorite = favorite;
        this.user = user;
    }

    public Exercise() {

    }
}