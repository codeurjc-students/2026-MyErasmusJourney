package com.myerasmusjourney.backend.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "country"})})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String country;

    private String description;

    @OneToMany
    private List<Experience> experiences = new LinkedList<>();

    public City(){}

    public City(String name, String country, String description){
        this.country = country;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public void addExperience(Experience experience){
        this.experiences.add(experience);
    }
}
