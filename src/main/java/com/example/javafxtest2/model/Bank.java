package com.example.javafxtest2.model;

import com.example.javafxtest2.exception.AccountNotFoundException;
import com.example.javafxtest2.exception.CustomerNotFoundException;
import com.example.javafxtest2.exception.TransferToSameAccountException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;

    private static volatile Bank instance;
    private static final Object getInstanceLock = new Object();

    private final ConcurrentHashMap<String, Customer> customers;
    private final ConcurrentHashMap<String, Account> accounts;
    private final transient List<TransactionObserver> observers;
    private LocalDate virtualDate;

    private Bank() {
        this.customers = new ConcurrentHashMap<>();
        this.accounts = new ConcurrentHashMap<>();
        this.observers = new CopyOnWriteArrayList<>();
        this.virtualDate = LocalDate.of(2026, 1, 1);

        this.registerObserver(new ConsoleLoggerObserver());
        this.registerObserver(new FileLoggerObserver());
    }

    public static Bank getInstance() {
        Bank result = instance;
        if (result == null) {
            synchronized (getInstanceLock) {
                result = instance;
                if (result == null) {
                    instance = result = new Bank();
                }
            }
        }
        return result;
    }

    public static void setInstance(Bank loadedBank) {
        synchronized (getInstanceLock) {
            instance = loadedBank;
        }
    }

    public void registerObserver(TransactionObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void removeObserver(TransactionObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Transaction transaction) {
        for (TransactionObserver observer : observers) {
            try {
                observer.onTransactionRegistered(transaction);
            } catch (Exception ex) {
                System.err.println("Error invoking observer: " + ex.getMessage());
            }
        }
    }

    public LocalDate getVirtualDate() {
        return virtualDate;
    }

    public void setVirtualDate(LocalDate virtualDate) {
        this.virtualDate = virtualDate;
    }

    public void createCustomer(String firstName, String lastName, String nationalId) {
        if (customers.containsKey(nationalId)) {
            throw new IllegalArgumentException("Customer with this National ID already exists.");
        }
        Customer customer = new Customer(firstName, lastName, nationalId);
        customers.put(nationalId, customer);
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        Customer owner = account.getOwner();
        if (owner != null) {
            owner.addAccount(account);
        }
    }

    public Customer getCustomer(String nationalId) {
        Customer customer = customers.get(nationalId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with national ID " + nationalId + " not found");
        }
        return customer;
    }

    public Account getAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account with number " + accountNumber + " not found");
        }
        return account;
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public void transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal amount) {
        if (sourceAccountNumber.equals(destinationAccountNumber)) {
            throw new TransferToSameAccountException("Source and destination accounts cannot be the same");
        }

        Account source = getAccount(sourceAccountNumber);
        Account destination = getAccount(destinationAccountNumber);

        Account firstLock = source.getAccountNumber().compareTo(destination.getAccountNumber()) < 0 ? source : destination;
        Account secondLock = firstLock == source ? destination : source;

        firstLock.getLock().lock();
        try {
            secondLock.getLock().lock();
            try {
                source.withdraw(amount);
                destination.deposit(amount);
            } finally {
                secondLock.getLock().unlock();
            }
        } finally {
            firstLock.getLock().unlock();
        }
    }

    private void readObject(java.io.ObjectInputStream ois) throws java.io.IOException, ClassNotFoundException {
        ois.defaultReadObject();

        try {
            java.lang.reflect.Field observersField = Bank.class.getDeclaredField("observers");
            observersField.setAccessible(true);

            java.util.List<TransactionObserver> newObservers = new java.util.concurrent.CopyOnWriteArrayList<>();
            newObservers.add(new ConsoleLoggerObserver());
            newObservers.add(new FileLoggerObserver());

            observersField.set(this, newObservers);
        } catch (Exception e) {
            System.err.println("Failed to reinitialize bank observers: " + e.getMessage());
        }
    }
}