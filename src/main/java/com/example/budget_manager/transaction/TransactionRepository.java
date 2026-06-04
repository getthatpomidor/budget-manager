package com.example.budget_manager.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByAccountId(Long id);

    @Query("""
    SELECT t from Transaction t
    WHERE (:from IS NULL OR t.transactionDate>= :from)
    AND (:to IS NULL OR t.transactionDate<= :to)
    AND(:category IS NULL OR LOWER(t.category)=LOWER(:category))
    """)
    List<Transaction> filter(LocalDate from, LocalDate to, String category);

    // summary

    @Query("""
    SELECT COALESCE(SUM(t.amount),0) from Transaction t
    WHERE t.transactionType='INCOME'
""")
    BigDecimal totalIncome();

    @Query("""
    SELECT COALESCE(SUM(t.amount),0) from Transaction t
    WHERE t.transactionType='EXPANSE'
""")
    BigDecimal totalExpense();

    @Query("""
    SELECT t.category,SUM(t.amount) from Transaction t
    WHERE t.transactionType='EXPENSE'
    group by t.category
""")
    List<Object[]> expenseByCategory();


}
