package com.example.foodcoupon.service;

import com.example.foodcoupon.model.*;
import com.example.foodcoupon.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final FoodItemRepository foodItemRepository;

    public CouponService(CouponRepository couponRepository, UserRepository userRepository, TransactionRepository transactionRepository, FoodItemRepository foodItemRepository) {
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.foodItemRepository = foodItemRepository;
    }

    @Transactional
    public Coupon generateCouponForUser(Long userId, Double value) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Coupon c = new Coupon();
        c.setCode(UUID.randomUUID().toString().substring(0,8).toUpperCase());
        c.setValue(value);
        c.setIssuedAt(LocalDate.now());
        c.setUser(user);
        return couponRepository.save(c);
    }

    @Transactional
    public Transaction redeemCoupon(String code, Long userId, Long itemId) {
        Coupon c = couponRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Coupon not found"));
        if (c.isUsed()) throw new RuntimeException("Coupon already used");
        if (!c.getUser().getId().equals(userId)) throw new RuntimeException("Coupon does not belong to this user");

        FoodItem item = foodItemRepository.findById(itemId).orElseThrow(NoSuchElementException::new);

        Transaction t = new Transaction();
        t.setUser(c.getUser());
        t.setItem(item);
        t.setCoupon(c);
        t.setAmount(item.getPrice());
        t.setTimestamp(LocalDateTime.now());
        transactionRepository.save(t);

        c.setUsed(true);
        couponRepository.save(c);
        return t;
    }

    public List<Coupon> listUserCoupons(Long userId) {
        return couponRepository.findByUserId(userId);
    }
}
