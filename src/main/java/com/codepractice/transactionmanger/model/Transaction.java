package com.codepractice.transactionmanger.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@ToString

public class Transaction {
        private LocalDate transactionDate;
        private String vendor;
        private String type;
        private BigDecimal amount;
        private String category;

        public Transaction(LocalDate transactionDate, String vendor, String type, BigDecimal amount, String category) {
            this.transactionDate = transactionDate;
            this.vendor = vendor;
            this.type = type;
            this.amount = amount;
            this.category = category;
        }
        public LocalDate getTransactionDate() {
            return transactionDate;
        }
        public String getVendor() {
            return vendor;
        }
        public String getType() {
            return type;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public String getCategory() {
            return category;
        }


        @Override
        public String toString() {
            return "Transaction{" +
                    "transactionDate=" + transactionDate +
                    ", vendor='" + vendor + '\'' +
                    ", type='" + type + '\'' +
                    ", amount=" + amount +
                    ", category='" + category + '\'' +
                    '}';
        }
    }
