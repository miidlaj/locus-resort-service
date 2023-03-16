package com.midlaj.resort.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.midlaj.resort.util.Constants;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "resort_images")
public class ResortImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_image", nullable = false)
    private String originalImage;

    @Column(name = "resized_image", nullable = false)
    private String resizedImage;

    @ManyToOne
    @JoinColumn(name = "resort_id")
    @JsonIgnore
    private Resort resort;

    public ResortImages(String originalImage, String resizedImage, Resort resort) {
        this.originalImage = originalImage;
        this.resizedImage= resizedImage;
        this.resort = resort;
    }

    public ResortImages(Long id, String originalImage, String resizedImage, Resort resort) {
        this.id = id;
        this.originalImage = originalImage;
        this.resizedImage = resizedImage;
        this.resort = resort;
    }

    public ResortImages() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public String getResizedImage() {
        return resizedImage;
    }

    public void setResizedImage(String resizedImage) {
        this.resizedImage = resizedImage;
    }

    public Resort getResort() {
        return resort;
    }

    public void setResort(Resort resort) {
        this.resort = resort;
    }


    @Override
    public String toString() {
        return "ResortImages{" +
                "id=" + id +
                ", originalImage='" + originalImage + '\'' +
                ", resizedImage='" + resizedImage + '\'' +
                '}';
    }

    @Transient
    public String getOriginalImageLink(){
        return Constants.S3_BASE_URI + "/resortImages/extraImages/" + resort.getId() + "/" + this.originalImage;
    }

    @Transient
    public String getResizedImageLink(){
        return Constants.S3_BASE_URI + "/resortImages/extraImages/" + resort.getId() + "/" + this.resizedImage;
    }
}

