package main.java.com.example.myProject.models;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.nio.file.Path;

@Entity
public class Image implements Embeddable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imagePath;

    public Image(Long id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    public Image() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
