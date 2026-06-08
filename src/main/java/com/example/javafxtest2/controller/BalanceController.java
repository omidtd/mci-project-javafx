package com.example.javafxtest2.controller;

import com.example.javafxtest2.model.Account;
import com.example.javafxtest2.model.Bank;
import java.math.BigDecimal;

public class BalanceController {

    public String handleGetBalance(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return "ERROR: Account number cannot be empty.";
        }

        try {
            Account account = Bank.getInstance().getAccount(accountNumber.trim());
            BigDecimal balance = account.getBalance();
            return "SUCCESS: Balance is " + balance;
        } catch (Exception ex) {
            return "ERROR: " + ex.getMessage();
        }
    }
}