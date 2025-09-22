package com.example.foodcoupon.controller;

import com.example.foodcoupon.model.*;
import com.example.foodcoupon.repository.*;
import com.example.foodcoupon.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;
    private final CouponService couponService;

    public ApiController(UserRepository userRepository, FoodItemRepository foodItemRepository, CouponService couponService) {
        this.userRepository = userRepository;
        this.foodItemRepository = foodItemRepository;
        this.couponService = couponService;
    }

    @GetMapping("/food-items")
    public List<FoodItem> allItems() { return foodItemRepository.findAll(); }

    @PostMapping("/food-items")
    public FoodItem createFoodItem(@RequestBody FoodItem item) { return foodItemRepository.save(item); }

    @GetMapping("/users/{id}/coupons")
    public List<Coupon> userCoupons(@PathVariable Long id) { return couponService.listUserCoupons(id); }

    @PostMapping("/coupons/generate")
    public Coupon generate(@RequestParam Long userId, @RequestParam Double value) { return couponService.generateCouponForUser(userId, value); }

    @PostMapping("/coupons/redeem")
    public ResponseEntity<?> redeem(@RequestParam String code, @RequestParam Long userId, @RequestParam Long itemId) {
        try {
            Transaction t = couponService.redeemCoupon(code, userId, itemId);
            return ResponseEntity.ok(t);
        } catch (RuntimeException ex) {
            Map<String, String> body = new java.util.HashMap<>();
            body.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(body);
        }
    }
}
