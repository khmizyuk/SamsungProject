package com.example.login1703.Models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String firstName, lastName, email, id;
    public int countOfMarkers = 0;

    public User() {}

    public User(String firstName,
                String lastName,
                String email,
                String id,
                int countOfMarkers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.countOfMarkers = countOfMarkers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCountOfMarkers() {
        return countOfMarkers;
    }

    public void setCountOfMarkers(int countOfMarkers) {
        this.countOfMarkers = countOfMarkers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
