package com.myerasmusjourney.backend.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "UserInfo")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String displayName;

    @Column(unique = true)
    private String email;

    private String encodedPassword;

    private String studyLocation = null;

    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new LinkedList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new LinkedList<>();

    public User(){
        this.roles.add("USER");
    }

    public User(String name, String display, String email, String encodedPassword, String city, String country){
        this.fullName = name;
        this.displayName = display;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.roles.add("USER");
        if(city != null && country != null) this.studyLocation = city + ", " + country;
    }

    public User(String name, String display, String email, String encodedPassword, String city, String country, List<String> roles){
        this.fullName = name;
        this.displayName = display;
        this.email = email;
        this.encodedPassword = encodedPassword;
        if(city != null && country != null) this.studyLocation = city + ", " + country;
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

    public void setStudyLocation(String studyLocation) {
        this.studyLocation = studyLocation;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setExperiences(List<Experience> experiences){
        this.experiences = experiences;
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
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

    public String getStudyLocation() {
        return studyLocation;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void addExperience(Experience experience){
        this.experiences.add(experience);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment (Comment comment){
        this.comments.add(comment);
    }
}
