package com.pweb.MyWorkoutBuddy.model;

import jakarta.persistence.*;

@Entity
public class UserExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Exercise exercise;

    private int sets;
    private int reps;
    private boolean favorite;
}

