package com.example.foodcoupon.model;

import jakarta.persistence.*;

@Entity
public class FoodItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;

    public FoodItem() {}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public Double getPrice(){return price;}
    public void setPrice(Double price){this.price=price;}
}
