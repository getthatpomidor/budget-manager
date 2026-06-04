package com.example.budget_manager.account;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    public Account() {}

    public Long getId(){return id;}

    public void setId(Long id) {
        this.id = id;
    }
    public String getName(){return name;}
    public void setName(String name){
        this.name = name;
    }
    public BigDecimal getBalance(){return balance;}
    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }
}
