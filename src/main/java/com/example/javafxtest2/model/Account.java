package com.example.javafxtest2.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String accountNumber;
    private final Customer owner;
    private BigDecimal balance;
    private transient ReentrantLock lock;

    public Account(String accountNumber, Customer owner, BigDecimal initialBalance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getOwner() {
        return owner;
    }

    public ReentrantLock getLock() {
        if (lock == null) {
            lock = new ReentrantLock();
        }
        return lock;
    }

    public BigDecimal getBalance() {
        getLock().lock();
        try {
            return balance;
        } finally {
            getLock().unlock();
        }
    }

    protected void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public abstract void deposit(BigDecimal amount);
    public abstract void withdraw(BigDecimal amount);
}