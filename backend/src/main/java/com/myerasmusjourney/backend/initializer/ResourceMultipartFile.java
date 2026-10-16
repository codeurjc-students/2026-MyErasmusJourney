package com.myerasmusjourney.backend.initializer;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class ResourceMultipartFile implements MultipartFile {

    private final String filename;
    private final String contentType;
    private final byte[] content;

    public ResourceMultipartFile(String filename, String contentType) throws IOException {
        this.filename = filename;
        this.contentType = contentType;

        ClassPathResource resource = new ClassPathResource("public/" + filename);

        try (InputStream input = resource.getInputStream()) {
            this.content = input.readAllBytes();
        }
    }

    @Override
    public String getName() {
        return filename;
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content.clone();
    }

    @Override
    public InputStream getInputStream() {
        return new java.io.ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException {
        java.nio.file.Files.write(dest.toPath(), content);
    }
}
