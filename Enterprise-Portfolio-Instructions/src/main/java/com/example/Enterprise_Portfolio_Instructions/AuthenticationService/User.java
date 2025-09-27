package com.example.Enterprise_Portfolio_Instructions.AuthenticationService;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
public class User {

   @Id
   private String username;

   private String password;

   public String getUsername() {
       return username;
   }
    public void setUsername(String username) {
         this.username = username;
    }
    public String getPassword() {
       return password;
    }
    public void setPassword(String password) {
       this.password = password;
    }
    public User(){

    }

    public boolean isEmpty() {
        return this.username == null;
    }
}
