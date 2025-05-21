package com.codepractice.transactionmanger.service;

import com.codepractice.transactionmanger.model.Transaction;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionService transactionService;

    private List<Transaction> mockData;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        transactionService = Mockito.spy(new TransactionService());

        mockData = Arrays.asList(
                new Transaction(LocalDate.of(2020, 11, 1), "Morrisons", "card", new BigDecimal("10.40"), "Groceries"),
                new Transaction(LocalDate.of(2020, 10, 28), "CYBG", "direct debit", new BigDecimal("600.00"), "MyMonthlyDD"),
                new Transaction(LocalDate.of(2020, 9, 28), "PureGym", "direct debit", new BigDecimal("40.00"), "MyMonthlyDD"),
                new Transaction(LocalDate.of(2020, 10, 1), "M&S", "card", new BigDecimal("5.99"), "Groceries"),
                new Transaction(LocalDate.of(2020, 11, 28), "CYBG", "direct debit", new BigDecimal("200.00"), "MyMonthlyDD"),
                new Transaction(LocalDate.of(2020, 11, 28), "PureGym", "direct debit", new BigDecimal("40.00"), "Groceries"),
                new Transaction(LocalDate.of(2020, 9, 30), "McMillan", "internet", new BigDecimal("10.00"), " ")
        );

        doReturn(mockData).when(transactionService).loadTransactions();
    }

    @Test
    void GetTransactionsByCategorytest() throws IOException {
        List<Transaction> groceries = transactionService.getTransactionsByCategory("Groceries");

        assertEquals(3, groceries.size(), "Should return 3 groceries transactions");
        assertEquals("PureGym", groceries.get(0).getVendor(), "Most recent should be first (2020-11-28)");
        assertTrue(groceries.stream().allMatch(t -> "Groceries".equalsIgnoreCase(t.getCategory())));
    }

    @Test
    void GetTotalOutgoingPerCategorytest() throws IOException {
        Map<String, BigDecimal> totals = transactionService.getTotalOutgoingPerCategory();

        assertEquals(new BigDecimal("56.39"), totals.get("Groceries"));      // 10.40 + 5.99 + 40.00
        assertEquals(new BigDecimal("840.00"), totals.get("MyMonthlyDD"));   // 600 + 40 + 200
        assertEquals(new BigDecimal("10.00"), totals.get(" "));              // one record with blank category

        assertEquals(3, totals.size(), "Should contain 3 categories");
    }
}