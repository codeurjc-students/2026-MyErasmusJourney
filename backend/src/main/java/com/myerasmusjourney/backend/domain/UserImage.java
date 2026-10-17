package com.myerasmusjourney.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Entity
public class UserImage extends Multimedia{
    private static final Set<String> EXTENSIONS = Set.of("image/jpeg", "image/jpg", "image/pjpeg", "image/png", "image/x-png", "image/gif");

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
        return EXTENSIONS.contains(contentType);
    }
}