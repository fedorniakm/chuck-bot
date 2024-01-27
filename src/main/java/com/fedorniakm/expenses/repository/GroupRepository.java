package com.fedorniakm.expenses.repository;

import com.fedorniakm.expenses.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> { }
