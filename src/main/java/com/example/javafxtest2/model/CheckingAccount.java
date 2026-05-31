package com.example.javafxtest2.model;

import com.example.javafxtest2.exception.InsufficientFundsException;
import com.example.javafxtest2.exception.InvalidAmountException;
import com.example.javafxtest2.model.TimeObserver;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CheckingAccount extends Account implements TimeObserver {
    private static final long serialVersionUID = 1L;

    private final BigDecimal transactionFee;
    private final BigDecimal monthlyMaintenanceFee;

    public CheckingAccount(String accountNumber, Customer owner, BigDecimal initialBalance,
                           BigDecimal transactionFee, BigDecimal monthlyMaintenanceFee) {
        super(accountNumber, owner, initialBalance);
        this.transactionFee = transactionFee;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    @Override
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }

        getLock().lock();
        try {
            setBalance(getBalance().add(amount));
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }

        getLock().lock();
        try {
            BigDecimal totalDeduction = amount.add(transactionFee);
            if (getBalance().compareTo(totalDeduction) < 0) {
                throw new InsufficientFundsException("Insufficient funds to cover amount and transaction fee");
            }
            setBalance(getBalance().subtract(totalDeduction));
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public void onDateChanged(LocalDate newDate, boolean monthChanged) {
        if (monthChanged) {
            getLock().lock();
            try {
                if (getBalance().compareTo(monthlyMaintenanceFee) < 0) {
                    setBalance(BigDecimal.ZERO);
                } else {
                    setBalance(getBalance().subtract(monthlyMaintenanceFee));
                }
            } finally {
                getLock().unlock();
            }
        }
    }
}