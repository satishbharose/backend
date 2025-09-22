package com.example.foodcoupon.repository;

import com.example.foodcoupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByUserId(Long userId);
    Optional<Coupon> findByCode(String code);
}
