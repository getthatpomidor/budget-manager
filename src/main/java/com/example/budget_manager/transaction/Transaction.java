package com.example.budget_manager.transaction;

import com.example.budget_manager.account.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "transaction_type")
    private TransactionType transactionType;

    @Column(nullable = false)
    private String category;
    private String description;

    @Column(nullable = false, name="transaction_date")
    private LocalDate transactionDate;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction() {}

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }
    public BigDecimal getAmount() { return this.amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public TransactionType getType() { return this.transactionType; }
    public void setType(TransactionType transactionType) { this.transactionType = transactionType; }
    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getTransactionDate() { return this.transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
    public Account getAccount() { return this.account; }
    public void setAccount(Account account) { this.account = account; }



}
