package com.example.foodcoupon.repository;

import com.example.foodcoupon.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
