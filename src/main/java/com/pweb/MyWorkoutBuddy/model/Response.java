package com.pweb.MyWorkoutBuddy.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private boolean success;
    private String message;
    private Object data;
}
