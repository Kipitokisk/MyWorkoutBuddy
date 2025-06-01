package com.example.MyWorkoutBuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "exercises")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}