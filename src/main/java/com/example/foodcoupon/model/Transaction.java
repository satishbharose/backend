package com.example.foodcoupon.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private FoodItem item;
    @ManyToOne
    private Coupon coupon;
    private Double amount;
    private LocalDateTime timestamp;

    public Transaction() {}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}
    public FoodItem getItem(){return item;}
    public void setItem(FoodItem item){this.item=item;}
    public Coupon getCoupon(){return coupon;}
    public void setCoupon(Coupon coupon){this.coupon=coupon;}
    public Double getAmount(){return amount;}
    public void setAmount(Double amount){this.amount=amount;}
    public LocalDateTime getTimestamp(){return timestamp;}
    public void setTimestamp(LocalDateTime timestamp){this.timestamp=timestamp;}
}
