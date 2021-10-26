package com.example.myProject.models;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double price;
    private String name;
    private ProductsEnum type;

    public Product() {
    }

    public Product(double price, String name, ProductsEnum type) {
        this.price = price;
        this.name = name;
        this.type = type;
    }

    public ProductsEnum getType() {
        return type;
    }

    public void setType(ProductsEnum type) {
        this.type = type;
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
}
