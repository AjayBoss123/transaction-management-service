package com.codepractice.transactionmanger.service;
import com.codepractice.transactionmanger.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

@Service
public class TransactionService {
    private final ObjectMapper objectMapper;
    public TransactionService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    public List<Transaction> loadTransactions() throws IOException {
        ClassPathResource resource = new ClassPathResource("transactions.json");
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Transaction>>(){});
        }
    }

    //Requirement 1: get all the transactions
    public List<Transaction> getTransactionsByCategory(String category) throws IOException {
        List<Transaction> transactions = loadTransactions();

        return transactions.stream()
                .filter(t -> category.equalsIgnoreCase(t.getCategory()))
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .collect(Collectors.toList());
    }

    //Requirement 2: Get total outgoing per category
    public Map<String, BigDecimal> getTotalOutgoingPerCategory() throws IOException {
        List<Transaction> transactions = loadTransactions();
        return transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));
    }

    // Requirement 3: Get Monthly Average Spend in a Given Category
    public BigDecimal getMonthlyAverageSpentPerCategory(String category) throws IOException {
        List<Transaction> transactions = loadTransactions();
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> t.getCategory() != null && t.getCategory().equalsIgnoreCase(category))
                .toList();
        Map<YearMonth, BigDecimal> monthlySpend = filteredTransactions.stream()
                .collect(Collectors.groupingBy(
                        t -> YearMonth.from(t.getTransactionDate()),
                        Collectors.mapping(
                                Transaction::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        if (monthlySpend.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalSpend = monthlySpend.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpend.divide(BigDecimal.valueOf(monthlySpend.size()), 2, RoundingMode.HALF_UP);
    }

    //Requirement 4: Highest spend in the given year for a category
    public BigDecimal getHighestSpendPerCategoryPerYear(String category, int year) throws IOException {
        List<Transaction> transactions = loadTransactions();
        return transactions.stream()
                .filter(t -> t.getCategory() != null && t.getCategory().equalsIgnoreCase(category))
                .filter(t -> t.getTransactionDate().getYear() == year)
                .map(Transaction::getAmount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
    //Requirement 5: Lowest spend in the given year for a category
    public BigDecimal getLowestSpendPerCategoryPerYear(String category, int year) throws IOException {
        List<Transaction> transactions = loadTransactions();
        return transactions.stream()
                .filter(t -> t.getCategory() != null && t.getCategory().equalsIgnoreCase(category))
                .filter(t -> t.getTransactionDate().getYear() == year)
                .map(Transaction::getAmount)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}




