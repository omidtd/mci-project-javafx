package com.example.javafxtest2.controller;

import com.example.javafxtest2.model.Bank;
import com.example.javafxtest2.model.CheckingAccount;
import com.example.javafxtest2.model.Customer;
import com.example.javafxtest2.model.SavingsAccount;

import java.math.BigDecimal;
import java.util.UUID;

public class OpenAccountController {

    public String handleOpenSavingsAccount(String nationalId, String initialBalanceStr, String interestRateStr) {
        if (nationalId == null || nationalId.trim().isEmpty()) {
            return "ERROR: National ID cannot be empty.";
        }
        if (initialBalanceStr == null || initialBalanceStr.trim().isEmpty()) {
            return "ERROR: Initial balance cannot be empty.";
        }
        if (interestRateStr == null || interestRateStr.trim().isEmpty()) {
            return "ERROR: Interest rate cannot be empty.";
        }

        try {
            Customer owner = Bank.getInstance().getCustomer(nationalId.trim());

            String accountNumber = UUID.randomUUID().toString().substring(0, 8);
            BigDecimal initialBalance = new BigDecimal(initialBalanceStr.trim());
            BigDecimal interestRate = new BigDecimal(interestRateStr.trim());

            SavingsAccount account = new SavingsAccount(accountNumber, owner, initialBalance, interestRate);
            Bank.getInstance().addAccount(account);

            return "SUCCESS: Savings Account opened with Number: " + accountNumber;
        } catch (Exception ex) {
            return "ERROR: " + ex.getMessage();
        }
    }

    public String handleOpenCheckingAccount(String nationalId, String initialBalanceStr, String maintenanceFeeStr) {
        if (nationalId == null || nationalId.trim().isEmpty()) {
            return "ERROR: National ID cannot be empty.";
        }
        if (initialBalanceStr == null || initialBalanceStr.trim().isEmpty()) {
            return "ERROR: Initial balance cannot be empty.";
        }
        if (maintenanceFeeStr == null || maintenanceFeeStr.trim().isEmpty()) {
            return "ERROR: Monthly maintenance fee cannot be empty.";
        }

        try {
            Customer owner = Bank.getInstance().getCustomer(nationalId.trim());

            String accountNumber = UUID.randomUUID().toString().substring(0, 8);
            BigDecimal initialBalance = new BigDecimal(initialBalanceStr.trim());
            BigDecimal transactionFee = new BigDecimal("1000");
            BigDecimal monthlyMaintenanceFee = new BigDecimal(maintenanceFeeStr.trim());

            CheckingAccount account = new CheckingAccount(accountNumber, owner, initialBalance, transactionFee, monthlyMaintenanceFee);
            Bank.getInstance().addAccount(account);

            return "SUCCESS: Checking Account opened with Number: " + accountNumber;
        } catch (Exception ex) {
            return "ERROR: " + ex.getMessage();
        }
    }
}