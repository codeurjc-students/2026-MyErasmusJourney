package com.myerasmusjourney.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private LocalDate date;

    private String title;

    private String description;

    public Experience(){}

    public Experience(String title, String description){
        this.title = title;
        this.description = description;
        this.date = LocalDate.now();
    }

    public Long getId(){
        return this.id;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

}
