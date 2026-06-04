package com.example.budget_manager.summary;

import com.example.budget_manager.summary.DTO.SummaryResponse;
import com.example.budget_manager.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    private TransactionRepository transactionRepository;
    public SummaryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public SummaryResponse getSummary() {
        BigDecimal totalIncome=transactionRepository.totalIncome();
        BigDecimal totalExpense=transactionRepository.totalExpense();
        Map<String,BigDecimal> expenseByCategory=transactionRepository.expenseByCategory().stream()
                .collect(Collectors.toMap(row->(String) row[0], row-> (BigDecimal) row[1]));
        return new SummaryResponse(totalIncome,totalExpense,expenseByCategory);}

}
