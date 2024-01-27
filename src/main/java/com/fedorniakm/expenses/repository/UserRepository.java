package com.fedorniakm.expenses.repository;

import com.fedorniakm.expenses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { }
