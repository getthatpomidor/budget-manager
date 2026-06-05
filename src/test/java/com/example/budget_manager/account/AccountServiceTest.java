package com.example.budget_manager.account;

import com.example.budget_manager.account.DTO.AccountResponse;
import com.example.budget_manager.account.DTO.CreateAccountRequest;
import com.example.budget_manager.exceptions.ConflictException;
import com.example.budget_manager.exceptions.ResourceNotFoundException;
import com.example.budget_manager.transaction.Transaction;
import com.example.budget_manager.transaction.TransactionRepository;
import com.example.budget_manager.transaction.TransactionType;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void createAccountTest() {
        CreateAccountRequest request = new CreateAccountRequest("test");
        AccountResponse response=accountService.createAccount(request);
        AccountResponse acc=accountService.getById(response.id());
        assertEquals("test",acc.name());
        assertEquals(BigDecimal.ZERO,acc.balance());

    }
    @Test
    void shouldThrowConflictWhenTransactionsExist() {
        Account account = new Account();
        account.setName("test");
        account.setBalance(BigDecimal.ZERO);

        Account saved = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.ONE);
        transaction.setCategory("OTHER");
        transaction.setTransactionDate(LocalDate.now());
        transaction.setType(TransactionType.INCOME);
        transactionRepository.save(transaction);

        assertThrows(ConflictException.class,
                () -> accountService.deleteAccount(saved.getId()));
    }

    @Test
    void shouldThrowResourceNotFoundWhenAccountDoesNotExist(){
        assertThrows(ResourceNotFoundException.class, ()->accountService.getById(100L));

    }
}