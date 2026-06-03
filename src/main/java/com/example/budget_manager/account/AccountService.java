package com.example.budget_manager.account;

import com.example.budget_manager.account.DTO.CreateAccountRequest;
import com.example.budget_manager.transaction.TransactionRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final TransactionRepository transactionRepository;
    public AccountService(AccountRepository repository,  TransactionRepository transactionRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }
    public Account createAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setName(request.name());
        account.setBalance(BigDecimal.ZERO);

        return repository.save(account);
    }

    public Account getById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));
    }
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    public void deleteAccount(Long id) {
        Account account= repository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));
        if(transactionRepository.existsByAccountId(id)) {
            throw new IllegalStateException("Cannot delete account with transactions");
        }
        repository.delete(account);
    }

}
