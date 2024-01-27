package com.fedorniakm.expenses.repository;

import com.fedorniakm.expenses.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> { }
