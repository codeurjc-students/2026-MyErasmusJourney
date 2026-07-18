package com.myerasmusjourney.backend.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String displayName;

    @Column(unique = true)
    private String email;

    private String password;

    private List<String> roles = new ArrayList<>();

    public User(String name, String display, String email, String password){
        this.fullName = name;
        this.displayName = display;
        this.email = email;
        this.password = password;
        this.roles.add("USER");
    }

    public User(String name, String display, String email, String password, List<String> roles){
        this.fullName = name;
        this.displayName = display;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
}
