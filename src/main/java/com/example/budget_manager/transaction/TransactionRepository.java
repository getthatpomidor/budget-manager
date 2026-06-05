package com.example.budget_manager.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByAccountId(Long id);
    List<Transaction> findByAccountId(Long id);

    @Query("""
    SELECT t FROM Transaction t
    WHERE t.transactionDate >= COALESCE(:from, t.transactionDate)
    AND t.transactionDate <= COALESCE(:to, t.transactionDate)
    AND t.category = COALESCE(:category, t.category)
""")
    List<Transaction> filter(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("category") String category
    );
    // summary

    @Query("""
    SELECT COALESCE(SUM(t.amount),0) from Transaction t
    WHERE t.transactionType='INCOME'
""")
    BigDecimal totalIncome();

    @Query("""
    SELECT COALESCE(SUM(t.amount),0) from Transaction t
    WHERE t.transactionType='EXPENSE'
""")
    BigDecimal totalExpense();

    @Query("""
    SELECT t.category,SUM(t.amount) from Transaction t
    WHERE t.transactionType='EXPENSE'
    group by t.category
""")
    List<Object[]> expenseByCategory();


}
