package com.example.javafxtest2.model;

import com.example.javafxtest2.exception.InsufficientFundsException;
import com.example.javafxtest2.exception.InvalidAmountException;
import com.example.javafxtest2.model.TimeObserver;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsAccount extends Account implements TimeObserver {
    private static final long serialVersionUID = 1L;

    private final BigDecimal interestRate;

    public SavingsAccount(String accountNumber, Customer owner, BigDecimal initialBalance, BigDecimal interestRate) {
        super(accountNumber, owner, initialBalance);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
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
            if (getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds in savings account");
            }
            setBalance(getBalance().subtract(amount));
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public void onDateChanged(LocalDate newDate, boolean monthChanged) {
        if (monthChanged) {
            getLock().lock();
            try {
                BigDecimal interest = getBalance().multiply(interestRate);
                if (interest.compareTo(BigDecimal.ZERO) > 0) {
                    setBalance(getBalance().add(interest));
                }
            } finally {
                getLock().unlock();
            }
        }
    }
}