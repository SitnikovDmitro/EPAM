package com.ra.model.entity;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {
    @Column(name = "role", nullable = false)
    private int role;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Id
    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;


    public User() {}

    public User(int role, String password, String username) {
        this.role = role;
        this.password = password;
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
