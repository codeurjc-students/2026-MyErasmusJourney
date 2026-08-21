package com.myerasmusjourney.backend.domain;

import com.myerasmusjourney.backend.enumeration.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private LocalDate date;

    private Float rating;

    private String title;

    private String description;

    private final Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Experience(){}

    public Experience(String title, String description, Float rating, LocalDate date, List<String> categories, City city, User user){
        this.title = title;
        this.rating = rating;
        this.description = description;
        if (date != null){
            this.date = date;
        }
        else{
            this.date = LocalDate.now();
        }
        this.city = city;
        this.author = user;
        for(String c: categories){
            this.categories.add(Category.valueOf(c));
        }
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        for(String c: categories){
            this.categories.add(Category.valueOf(c));
        }
    }
}
