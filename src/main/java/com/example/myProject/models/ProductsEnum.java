package com.example.myProject.models;

public enum ProductsEnum {
    IRON("Утюг"),
    VacuumCleaner("Пылесос"),
    WashingMachine("Стиральная машина"),
    PHONE("Телефон"),
    TV("Телевизор"),
    COMPUTER("Компьютер"),
    CoffeeMachine("Кофемашина"),
    FRIDGE("Холодильник");

    private String title;

    ProductsEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}


