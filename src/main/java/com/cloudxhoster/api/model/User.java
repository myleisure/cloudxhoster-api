package com.cloudxhoster.api.model;

import java.util.Date;
import java.util.StringJoiner;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private boolean verified;
    private Date creationDate;

    public User() {
    }

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("verified=" + verified)
                .add("creationDate=" + creationDate)
                .toString();
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public boolean isVerified() { return verified; }

    public void setVerified(boolean verified) { this.verified = verified; }

    public Date getCreationDate() { return creationDate; }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
}
