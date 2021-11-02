package com.example.myProject.models;

public enum ProductType {
    IRON("Утюг"),
    VacuumCleaner("Пылесос"),
    WashingMachine("Стиральная машина"),
    PHONE("Телефон"),
    TV("Телевизор"),
    COMPUTER("Компьютер"),
    CoffeeMachine("Кофемашина"),
    FRIDGE("Холодильник");

    private String title;

    ProductType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}


