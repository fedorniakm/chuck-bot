package com.fedorniakm.expenses.repository;

import com.fedorniakm.expenses.entity.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<PlatformUser, Long> { }
