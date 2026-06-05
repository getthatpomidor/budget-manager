package com.example.budget_manager.transaction;

import com.example.budget_manager.account.Account;
import com.example.budget_manager.account.AccountRepository;
import com.example.budget_manager.exceptions.ConflictException;
import com.example.budget_manager.exceptions.ResourceNotFoundException;
import com.example.budget_manager.transaction.DTO.CreateTransactionRequest;
import com.example.budget_manager.transaction.DTO.TransactionResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {

        Account account = accountRepository.findById(request.accountId()).orElseThrow(()->new ResourceNotFoundException("Account not found"));
        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setType(request.transactionType());
        transaction.setCategory(request.category().trim().toUpperCase());
        transaction.setTransactionDate(request.transactionDate());
        transaction.setAccount(account);

        if(request.transactionType()== TransactionType.INCOME){
            account.setBalance(account.getBalance().add(request.amount()));
        }else{
            if(account.getBalance().compareTo(request.amount()) < 0){
                throw new ConflictException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(request.amount()));}

        Transaction saved =transactionRepository.save(transaction);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(()->new ResourceNotFoundException("Transaction not found"));
        Account account = transaction.getAccount();

        if(transaction.getType()==TransactionType.INCOME){
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        }else{
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        }
        transactionRepository.delete(transaction);
    }
    public List<TransactionResponse> getAllTransactions(LocalDate from, LocalDate to, String category) {
        String normalizedCategory =
                category != null ? category.trim().toUpperCase() : null;
        return transactionRepository.filter(from, to, normalizedCategory).stream().map(this::mapToResponse).toList();
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getAccount().getId()
        );
    }

    public byte[] exportTransactions(Long accountId){
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        StringWriter sw = new StringWriter();
        sw.append("id,amount,type,category,description,date\n");

        for(Transaction transaction : transactions){
            sw.append(transaction.getId().toString()).append(",");
            sw.append(transaction.getAmount().toString()).append(",");
            sw.append(transaction.getType().name()).append(",");
            sw.append(transaction.getCategory()).append(",");
            sw.append(Optional.ofNullable(transaction.getDescription()).orElse("")).append(",");
            sw.append(transaction.getTransactionDate().toString()).append("\n");
        }

        return sw.toString().getBytes(StandardCharsets.UTF_8);
    }


}
