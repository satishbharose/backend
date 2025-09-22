package com.example.foodcoupon.config;

import com.example.foodcoupon.model.*;
import com.example.foodcoupon.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, FoodItemRepository foodItemRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.foodItemRepository = foodItemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void load() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("Admin");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole("ADMIN");
            userRepository.save(admin);

            User u = new User();
            u.setName("Satish");
            u.setUsername("satish");
            u.setPassword(passwordEncoder.encode("password"));
            u.setRole("USER");
            userRepository.save(u);

            FoodItem f1 = new FoodItem(); f1.setName("Veg Thali"); f1.setPrice(80.0); foodItemRepository.save(f1);
            FoodItem f2 = new FoodItem(); f2.setName("Sandwich"); f2.setPrice(40.0); foodItemRepository.save(f2);
        }
    }
}
