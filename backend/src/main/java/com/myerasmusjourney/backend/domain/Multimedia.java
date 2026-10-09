package com.myerasmusjourney.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

@MappedSuperclass
public abstract class Multimedia {
    private static final Map<String, String> EXTENSIONS = Map.ofEntries(Map.entry("image/jpeg", ".jpg"), Map.entry("image/jpg", ".jpg"), Map.entry("image/pjpeg", ".jpg"), Map.entry("image/png", ".png"), Map.entry("image/x-png", ".png"), Map.entry("image/gif", ".gif"), Map.entry("video/mp4", ".mp4"), Map.entry("video/webm", ".webm"), Map.entry("video/quicktime", ".mov"), Map.entry("video/x-msvideo", ".avi"), Map.entry("video/x-matroska", ".mkv"));

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
        this.contentType = (String)EXTENSIONS.getOrDefault(rawImage.getContentType(), ".jpg");
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
        this.contentType = (String)EXTENSIONS.get(contentType);
    }

    public static boolean contentTypeAccepted(String contentType) {
        return EXTENSIONS.containsKey(contentType);
    }
}
