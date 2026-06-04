package com.example.budget_manager.account;

import com.example.budget_manager.account.DTO.AccountResponse;
import com.example.budget_manager.account.DTO.CreateAccountRequest;
import com.example.budget_manager.exceptions.ConflictException;
import com.example.budget_manager.exceptions.ResourceNotFoundException;
import com.example.budget_manager.transaction.TransactionRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final TransactionRepository transactionRepository;
    public AccountService(AccountRepository repository,  TransactionRepository transactionRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }
    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setName(request.name());
        account.setBalance(BigDecimal.ZERO);

        Account saved= repository.save(account);
        return mapToResponse(saved);
    }

    public AccountResponse getById(Long id) {
        Account account = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Account not found"));
        return mapToResponse(account);
    }
    public List<AccountResponse> getAllAccounts() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    public void deleteAccount(Long id) {
        Account account= repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Account not found"));
        if(transactionRepository.existsByAccountId(id)) {
            throw new ConflictException("Cannot delete account with transactions");
        }
        repository.delete(account);
    }

    private AccountResponse mapToResponse(Account account) {
        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }

}
