package com.codepractice.transactionmanger.controller;

import com.codepractice.transactionmanger.model.Transaction;
import com.codepractice.transactionmanger.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/category/{category}")
    public List<Transaction>
    getTransactionsByCategory(@PathVariable String category) throws IOException
    {
        return transactionService.getTransactionsByCategory(category);
    }
    @GetMapping("/total-outgoing")
    public ResponseEntity<Map<String, BigDecimal>> getTotalOutgoingPerCategory() {
        try {
            Map<String, BigDecimal> totalOutgoing = transactionService.getTotalOutgoingPerCategory();
            return ResponseEntity.ok(totalOutgoing);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyMap());
        }
    }
    //Monthly average spend in the given catgegory
    @GetMapping("/average-spend/{category}")
    public ResponseEntity<BigDecimal> getAverageSpend(@PathVariable String category) throws IOException {
        BigDecimal result = transactionService.getMonthlyAverageSpentPerCategory(category);
        return ResponseEntity.ok(result);
    }
    //Highest spend per category per year
    @GetMapping("/highest-spend/{category}/{year}")
    public ResponseEntity <BigDecimal> getHighestSpend(@PathVariable String category,@PathVariable int year)throws IOException{
        BigDecimal result=transactionService.getHigheSpendPerCategoryPerYear(category, year);
        return ResponseEntity.ok(result);
    }
    //Lowest spend per category per year
    @GetMapping("/lowest-spend/{category}/{year}")
    public ResponseEntity<BigDecimal> getLowestSpend(@PathVariable String category,@PathVariable int year)throws IOException{
        BigDecimal result=transactionService.getLowestSpentPerCategoryPerYear(category,year);
        return ResponseEntity.ok(result);
    }
}

