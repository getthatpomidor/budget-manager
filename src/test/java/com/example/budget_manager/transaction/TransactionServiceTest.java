package com.example.budget_manager.transaction;

import com.example.budget_manager.account.Account;
import com.example.budget_manager.account.AccountRepository;
import com.example.budget_manager.exceptions.ConflictException;
import com.example.budget_manager.exceptions.ResourceNotFoundException;
import com.example.budget_manager.transaction.DTO.CreateTransactionRequest;
import com.example.budget_manager.transaction.DTO.TransactionResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void shouldIncreaseBalanceWhenIncome(){
        Account account = new Account();
        account.setName("test");
        account.setBalance(BigDecimal.valueOf(100));
        account=accountRepository.save(account);

        CreateTransactionRequest request = new CreateTransactionRequest(
                BigDecimal.valueOf(50),
                TransactionType.INCOME,
                "OTHER",
                "test",
                LocalDate.now(),
                account.getId()
        );

        transactionService.createTransaction(request);

        Account updatedAccount = accountRepository.findById(account.getId()).get();

        assertEquals(BigDecimal.valueOf(150), updatedAccount.getBalance());
    }

    @Test
    void shouldDecreaseBalanceWhenExpense(){
        Account account = new Account();
        account.setName("test");
        account.setBalance(BigDecimal.valueOf(100));
        account=accountRepository.save(account);

        CreateTransactionRequest request = new CreateTransactionRequest(
                BigDecimal.valueOf(50),
                TransactionType.EXPENSE,
                "OTHER",
                "test",
                LocalDate.now(),
                account.getId()
        );

        transactionService.createTransaction(request);

        Account updatedAccount = accountRepository.findById(account.getId()).get();

        assertEquals(BigDecimal.valueOf(50), updatedAccount.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist(){

        CreateTransactionRequest request = new CreateTransactionRequest(
                BigDecimal.valueOf(150),
                TransactionType.EXPENSE,
                "OTHER",
                "test",
                LocalDate.now(),
                Long.valueOf(100)
        );

        assertThrows(ResourceNotFoundException.class, ()->{
            transactionService.createTransaction(request);
        });
    }

    @Test
    void shouldThrowExceptionWhenInsufficientBalance(){
        Account account = new Account();
        account.setName("test");
        account.setBalance(BigDecimal.valueOf(100));
        account=accountRepository.save(account);

        CreateTransactionRequest request = new CreateTransactionRequest(
                BigDecimal.valueOf(150),
                TransactionType.EXPENSE,
                "OTHER",
                "test",
                LocalDate.now(),
                account.getId()
        );

        assertThrows(ConflictException.class, ()->{
            transactionService.createTransaction(request);
        });
    }

    @Test
    void shouldRevertBalanceWhenDeleteIncome(){
        Account account = new Account();
        account.setName("test");
        account.setBalance(BigDecimal.ZERO);
        account = accountRepository.save(account);

        CreateTransactionRequest request = new CreateTransactionRequest(
                BigDecimal.valueOf(100),
                TransactionType.INCOME,
                "OTHER",
                "test",
                LocalDate.now(),
                account.getId()
        );

        TransactionResponse created = transactionService.createTransaction(request);

        transactionService.deleteTransaction(created.id());

        Account updated = accountRepository.findById(account.getId()).get();

        assertEquals(BigDecimal.ZERO, updated.getBalance());
    }

    @Test
    void shouldRevertBalanceWhenDeleteEXPANSE(){
        Account account = new Account();
        account.setName("test");
        account.setBalance(BigDecimal.valueOf(100));
        account = accountRepository.save(account);

        CreateTransactionRequest request = new CreateTransactionRequest(
                BigDecimal.valueOf(100),
                TransactionType.EXPENSE,
                "OTHER",
                "test",
                LocalDate.now(),
                account.getId()
        );

        TransactionResponse created = transactionService.createTransaction(request);

        transactionService.deleteTransaction(created.id());

        Account updated = accountRepository.findById(account.getId()).get();

        assertEquals(BigDecimal.valueOf(100), updated.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenDeleteNotExistingTransaction(){
        Account account = new Account();
        account.setName("test");
    }

}
