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

    private String encodedPassword;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public User(){
        this.roles.add("USER");
    }

    public User(String name, String display, String email, String encodedPassword){
        this.fullName = name;
        this.displayName = display;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.roles.add("USER");
    }

    public User(String name, String display, String email, String encodedPassword, List<String> roles){
        this.fullName = name;
        this.displayName = display;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public void setRoles(List<String> roles) {
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

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public List<String> getRoles() {
        return roles;
    }
}
