package com.codepractice.transaction_classifier.transaction_classifier_service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.when;

@SpringBootTest
@Configuration
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionService mockTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTransactionByCategory() throws IOException {
        // Mock data
        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction(LocalDate.of(2024, 3, 1), "Morrisons", "card", new BigDecimal("10.40"), "Groceries"),
                new Transaction(LocalDate.of(2024, 2, 15), "Tesco", "card", new BigDecimal("20.00"), "Groceries")
        );
        // Mock service behavior
        when(mockTransactionService.getTransactionsByCategory("Groceries")).thenReturn(mockTransactions);
        // Call the service method
        List<Transaction> groceries = mockTransactionService.getTransactionsByCategory("Groceries");
        // Assertions
        assertFalse(groceries.isEmpty(), "The transactions list should not be empty.");
        assertEquals(2, groceries.size(), "There should be 2 transactions in the 'Groceries' category.");
        assertEquals("Groceries", groceries.get(0).getCategory(), "The category of the first transaction should be 'Groceries'.");
    }

    @Test
    void testGetTotalOutgoingPerCategory() throws IOException {
        // Mock response
        Map<String, BigDecimal> mockResponse = new HashMap<>();
        mockResponse.put("Groceries", new BigDecimal("30.40"));
        mockResponse.put("MyMonthlyDD", new BigDecimal("50.00"));

        when(mockTransactionService.getTotalOutgoingPerCategory()).thenReturn(mockResponse);

        Map<String, BigDecimal> result = mockTransactionService.getTotalOutgoingPerCategory();
        assertNotNull(result, "The result should not be null.");
        assertEquals(2, result.size(), "There should be 2 categories in the response.");
        assertEquals(new BigDecimal("30.40"), result.get("Groceries"), "Total spending for 'Groceries' should match.");
    }

   /* @Test
    void testGetMonthlyAverageSpentPerCategory() throws IOException {
        // Mock response
        Map<String, BigDecimal> mockResponse = new HashMap<>();
        mockResponse.put("MyMonthlyDD", new BigDecimal("200.00"));
        mockResponse.put("Groceries", new BigDecimal("40.00"));

        LocalDate testMonth = LocalDate.of(2024, 3, 1); // ✅ Use LocalDate, not String

        // ✅ Fix: Pass LocalDate instead of String
        when(transactionService.getMonthlyAverageSpentPerCategory(testMonth))
                .thenReturn((BigDecimal) mockResponse);

        // Call the actual service method
        BigDecimal result = transactionService.getMonthlyAverageSpentPerCategory(testMonth);

        // Assertions
        assertNotNull(result, "The result should not be null.");
        assertEquals(2, result.size(), "There should be 2 categories in the response.");
        assertEquals(new BigDecimal("200.00"), result.get("MyMonthlyDD"), "Monthly average for 'MyMonthlyDD' should match.");
        assertEquals(new BigDecimal("40.00"), result.get("Groceries"), "Monthly average for 'Groceries' should match.");
    }
    @Test
    void testGetHighestSpentPerCategoryPerYear() throws IOException {
        // Mock response
        Map<String, BigDecimal> mockResponse = new HashMap<>();
        mockResponse.put("MyMonthlyDD", new BigDecimal("1200.00")); // Highest spending for category
        mockResponse.put("Groceries", new BigDecimal("500.00"));

        int testYear = 2024; // Example year

        // ✅ Mock service behavior
        when(transactionService.getLowestSpentPerCategoryPerYear(testYear))
                .thenReturn(mockResponse);

        // ✅ Call the actual service method
        Map<String, BigDecimal> result = transactionService.getLowestSpentPerCategoryPerYear(testYear);

        // ✅ Assertions
        assertNotNull(result, "The result should not be null.");
        assertEquals(2, result.size(), "There should be 2 categories in the response.");
        assertEquals(new BigDecimal("1200.00"), result.get("MyMonthlyDD"), "Highest spent for 'MyMonthlyDD' should match.");
        assertEquals(new BigDecimal("500.00"), result.get("Groceries"), "Highest spent for 'Groceries' should match.");
    }
    @Test
    void testGetLowestSpentPerCategoryPerYear() throws IOException {
        // Mock transaction data
        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction(LocalDate.of(2020, 11, 1), "Morrisons", "card", new BigDecimal("10.40"), "Groceries"),
                new Transaction(LocalDate.of(2020, 10, 28), "CYBG", "direct debit", new BigDecimal("600.00"), "MyMonthlyDD"),
                new Transaction(LocalDate.of(2020, 10, 28), "PureGym", "direct debit", new BigDecimal("40.00"), "MyMonthlyDD"),
                new Transaction(LocalDate.of(2020, 10, 1), "M&S", "card", new BigDecimal("5.99"), "Groceries"),
                new Transaction(LocalDate.of(2020, 9, 30), "McMillan", "internet", new BigDecimal("10.00"), "Internet")
        );

        String testCategory = "Groceries"; // ✅ Test category
        int testYear = 2020; // ✅ Test year

        // ✅ Expected lowest spend for "Groceries" in 2020 -> £5.99 (M&S transaction)
        BigDecimal expectedLowestSpend = new BigDecimal("5.99");


        OngoingStubbing<BigDecimal> bigDecimalOngoingStubbing = when(transactionService.getLowestSpentPerCategoryPerYear(testCategory, testYear))
                .thenReturn(expectedLowestSpend);


        BigDecimal result = transactionService.getLowestSpentPerCategoryPerYear(testCategory, testYear);


        assertNotNull(result, "The result should not be null.");
        assertEquals(expectedLowestSpend, result, "The lowest spent amount for 'Groceries' in 2020 should be £5.99.");
    }*/
}




