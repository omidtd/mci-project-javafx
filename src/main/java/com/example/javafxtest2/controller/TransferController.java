package com.example.javafxtest2.controller;

import com.example.javafxtest2.model.Bank;
import com.example.javafxtest2.model.Transaction;
import com.example.javafxtest2.model.enums.TransactionStatus;
import com.example.javafxtest2.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransferController {

    public String handleTransfer(String sourceAccountNumber, String destinationAccountNumber, String amountStr) {
        if (sourceAccountNumber == null || sourceAccountNumber.trim().isEmpty()) {
            return "ERROR: Source account number cannot be empty.";
        }
        if (destinationAccountNumber == null || destinationAccountNumber.trim().isEmpty()) {
            return "ERROR: Destination account number cannot be empty.";
        }
        if (amountStr == null || amountStr.trim().isEmpty()) {
            return "ERROR: Amount cannot be empty.";
        }

        String transactionId = UUID.randomUUID().toString().substring(0, 8);
        BigDecimal amount = BigDecimal.ZERO;

        try {
            amount = new BigDecimal(amountStr.trim());

            Bank.getInstance().transfer(sourceAccountNumber.trim(), destinationAccountNumber.trim(), amount);

            Transaction successTx = new Transaction(
                    transactionId,
                    TransactionType.TRANSFER,
                    amount,
                    sourceAccountNumber.trim(),
                    destinationAccountNumber.trim(),
                    TransactionStatus.SUCCESS,
                    "Transfer of " + amount + " from " + sourceAccountNumber + " to " + destinationAccountNumber,
                    LocalDateTime.now()
            );

            Bank.getInstance().notifyObservers(successTx);

            return "SUCCESS: Transferred " + amount + ". Transaction ID: " + transactionId;
        } catch (Exception ex) {
            Transaction failedTx = new Transaction(
                    transactionId,
                    TransactionType.TRANSFER,
                    amount,
                    sourceAccountNumber.trim(),
                    destinationAccountNumber.trim(),
                    TransactionStatus.FAILED,
                    "FAILED: " + ex.getMessage(),
                    LocalDateTime.now()
            );

            Bank.getInstance().notifyObservers(failedTx);

            return "ERROR: " + ex.getMessage();
        }
    }
}