package com.myerasmusjourney.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Entity
public class UserImage extends Multimedia{
    private static final Map<String, String> EXTENSIONS = Map.ofEntries(Map.entry("image/jpeg", ".jpg"), Map.entry("image/jpg", ".jpg"), Map.entry("image/pjpeg", ".jpg"), Map.entry("image/png", ".png"), Map.entry("image/x-png", ".png"));

    @OneToOne(mappedBy = "profileImage")
    private User user;

    public UserImage(){}

    public UserImage(MultipartFile rawImage, User user) throws IOException {
        this.user = user;
        setContentType(rawImage.getContentType());
        setMultimediaFile(rawImage);
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public static boolean contentTypeAccepted( String contentType){
        return EXTENSIONS.containsKey(contentType);
    }
}