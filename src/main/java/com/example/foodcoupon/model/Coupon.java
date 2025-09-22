package com.example.foodcoupon.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Double value;
    private boolean used = false;
    private LocalDate issuedAt;

    @ManyToOne
    private User user;

    public Coupon() {}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getCode(){return code;}
    public void setCode(String code){this.code=code;}
    public Double getValue(){return value;}
    public void setValue(Double value){this.value=value;}
    public boolean isUsed(){return used;}
    public void setUsed(boolean used){this.used=used;}
    public LocalDate getIssuedAt(){return issuedAt;}
    public void setIssuedAt(LocalDate issuedAt){this.issuedAt=issuedAt;}
    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}
}
