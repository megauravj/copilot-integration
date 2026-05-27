package com.example.dto;

import java.util.Objects;

/**
 * Data Transfer Object for User with formatted creation date.
 */
public class UserDTO {
    
    private long id;
    private String name;
    private String email;
    private String formattedCreatedDate;
    
    public UserDTO(long id, String name, String email, String formattedCreatedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.formattedCreatedDate = formattedCreatedDate;
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
    
    public String getFormattedCreatedDate() {
        return formattedCreatedDate;
    }
    
    public void setFormattedCreatedDate(String formattedCreatedDate) {
        this.formattedCreatedDate = formattedCreatedDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(formattedCreatedDate, userDTO.formattedCreatedDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, formattedCreatedDate);
    }
    
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", formattedCreatedDate='" + formattedCreatedDate + '\'' +
                '}';
    }
}
