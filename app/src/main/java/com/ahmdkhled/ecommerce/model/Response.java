package com.ahmdkhled.ecommerce.model;

public class Response {
    private boolean error;
    private String message;
    private long id;
    private String name;
    private String email;

    public Response(boolean error, String message, long id, String name, String email) {
        this.error = error;
        this.message = message;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
