package com.example.myProject.models;

import javax.persistence.*;
import java.nio.file.Path;
import java.util.Set;

@Entity
public class Product  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String logo;
    private double price;
    private String name;
    private String description;



    public Product() {
    }

    public Product(String name, Double price, String description) {
        this.price = price;
        this.name = name;
        this.description = description;
    }



    @ElementCollection(targetClass = ProductType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "type", joinColumns = @JoinColumn(name = "type_id"))
    @Enumerated(EnumType.STRING)
    private Set<ProductType> types;




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Transient
    public String getImagePath() {
        if (logo == null || id == null) return null;

        return "/images/" + id + "/" + logo;
    }
}
