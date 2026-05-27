package com.example.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User entity with id, name, email, creation date, and active status.
 */
public class User {
    
    private long id;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private boolean active;
    
    public User(long id, String name, String email, LocalDateTime createdDate, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.active = active;
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
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                active == user.active &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(createdDate, user.createdDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, createdDate, active);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", active=" + active +
                '}';
    }
}
