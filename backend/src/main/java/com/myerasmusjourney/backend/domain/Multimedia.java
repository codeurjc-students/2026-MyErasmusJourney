package com.myerasmusjourney.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import java.io.IOException;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

@MappedSuperclass
public abstract class Multimedia {
    private static final Set<String> EXTENSIONS = Set.of("image/jpeg", "image/jpg", "image/pjpeg", "image/png", "image/x-png", "image/gif", "video/mp4", "video/webm", "video/quicktime", "video/x-msvideo", "video/x-matroska");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] multimediaFile;

    private String contentType;

    public Multimedia() {
    }

    public Multimedia(MultipartFile rawImage) throws IOException {
        this.contentType = rawImage.getContentType();
        this.multimediaFile = rawImage.getBytes();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getMultimediaFile() {
        return this.multimediaFile;
    }

    public void setMultimediaFile(MultipartFile rawImage) throws IOException {
        this.multimediaFile = rawImage.getBytes();
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public static boolean contentTypeAccepted(String contentType) {
        return EXTENSIONS.contains(contentType);
    }
}
