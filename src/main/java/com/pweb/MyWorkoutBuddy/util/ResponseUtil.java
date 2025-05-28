package com.pweb.MyWorkoutBuddy.util;

import com.pweb.MyWorkoutBuddy.model.Response;

public class ResponseUtil {

    private ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Response buildSuccessResponse(String message, Object data) {
        Response response = new Response();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static Response getErrorResponse(String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}

