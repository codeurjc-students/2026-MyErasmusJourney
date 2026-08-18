package com.myerasmusjourney.backend.domain;

import com.myerasmusjourney.backend.enumeration.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private LocalDate date;

    private Float rating;

    private String title;

    private String description;

    private Category category;

    @ManyToOne
    private City city;

    @ManyToOne
    private User author;

    public Experience(){}

    public Experience(String title, String description, Float rating, String category, City city, User user){
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.date = LocalDate.now();
        this.category = Category.valueOf(category);
        this.city = city;
        this.author = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public City getCity() {
        return city;
    }

    public Long getId(){
        return this.id;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public Float getRating() {
        return rating;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Category.valueOf(category);
    }
}
