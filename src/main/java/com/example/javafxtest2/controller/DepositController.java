package com.example.javafxtest2.controller;

import com.example.javafxtest2.model.Account;
import com.example.javafxtest2.model.Bank;
import com.example.javafxtest2.model.Transaction;
import com.example.javafxtest2.model.enums.TransactionStatus;
import com.example.javafxtest2.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DepositController {

    public String handleDeposit(String accountNumber, String amountStr) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return "ERROR: Account number cannot be empty.";
        }
        if (amountStr == null || amountStr.trim().isEmpty()) {
            return "ERROR: Amount cannot be empty.";
        }

        String transactionId = UUID.randomUUID().toString().substring(0, 8);
        BigDecimal amount = BigDecimal.ZERO;

        try {
            amount = new BigDecimal(amountStr.trim());
            Account account = Bank.getInstance().getAccount(accountNumber.trim());

            account.deposit(amount);

            Transaction successTx = new Transaction(
                    transactionId,
                    TransactionType.DEPOSIT,
                    amount,
                    null,
                    accountNumber.trim(),
                    TransactionStatus.SUCCESS,
                    "Deposit of " + amount + " to account " + accountNumber,
                    LocalDateTime.now()
            );

            Bank.getInstance().notifyObservers(successTx);

            return "SUCCESS: Deposited " + amount + ". Transaction ID: " + transactionId;
        } catch (Exception ex) {
            Transaction failedTx = new Transaction(
                    transactionId,
                    TransactionType.DEPOSIT,
                    amount,
                    null,
                    accountNumber.trim(),
                    TransactionStatus.FAILED,
                    "FAILED: " + ex.getMessage(),
                    LocalDateTime.now()
            );

            Bank.getInstance().notifyObservers(failedTx);

            return "ERROR: " + ex.getMessage();
        }
    }
}