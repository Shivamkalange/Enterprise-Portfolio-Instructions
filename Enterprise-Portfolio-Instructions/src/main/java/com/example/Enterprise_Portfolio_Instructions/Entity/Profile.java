package com.example.Enterprise_Portfolio_Instructions.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Entity
@Table(name = "EPI")
public class Profile {

    @Id
    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Company is mandatory")
    private String company;
    private String project;
    private String role;
    private String location;

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
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
        public String getCompany() {
            return company;
        }
        public void setCompany(String company) {
            this.company = company;
        }
        public String getProject() {
            return project;
        }
        public void setProject(String project) {
            this.project = project;
        }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Profile() {
    }


}
