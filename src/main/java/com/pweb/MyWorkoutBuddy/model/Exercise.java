package com.pweb.MyWorkoutBuddy.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @ElementCollection
    @CollectionTable(name = "exercise_targets", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "target")
    private List<String> targets;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String image;
}
