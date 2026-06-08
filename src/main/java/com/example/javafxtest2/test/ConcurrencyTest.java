package com.example.javafxtest2.test;

import com.example.javafxtest2.model.Account;
import com.example.javafxtest2.model.Bank;
import com.example.javafxtest2.model.Customer;
import com.example.javafxtest2.model.SavingsAccount;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTest {

    public static void main(String[] args) throws InterruptedException {
        Bank bank = Bank.getInstance();

        bank.createCustomer("Test", "User", "123456");
        Customer customer = bank.getCustomer("123456");

        String accNumber = "99999";
        BigDecimal initialBalance = new BigDecimal("100000");
        SavingsAccount account = new SavingsAccount(accNumber, customer, initialBalance, new BigDecimal("0.0"));
        bank.addAccount(account);

        int numberOfThreads = 100;
        BigDecimal withdrawAmount = new BigDecimal("500");

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numberOfThreads);

        System.out.println("--- Starting Concurrency Stress Test ---");
        System.out.println("Initial Balance: " + account.getBalance());

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await();

                    Account acc = Bank.getInstance().getAccount("99999");
                    acc.withdraw(withdrawAmount);

                } catch (Exception ignored) {
                } finally {
                    endLatch.countDown();
                }
            });
        }

        long startTime = System.nanoTime();

        startLatch.countDown();

        endLatch.await(5, TimeUnit.SECONDS);
        executorService.shutdown();

        long endTime = System.nanoTime();
        System.out.println("All threads finished in: " + ((endTime - startTime) / 1_000_000) + " ms");

        BigDecimal expectedBalance = initialBalance.subtract(withdrawAmount.multiply(new BigDecimal(numberOfThreads)));
        BigDecimal actualBalance = account.getBalance();

        System.out.println("Expected Final Balance: " + expectedBalance);
        System.out.println("Actual Final Balance: " + actualBalance);

        if (actualBalance.compareTo(expectedBalance) == 0) {
            System.out.println("TEST PASSED: System is 100% Safe against Race Conditions!");
        } else {
            System.out.println("TEST FAILED: Race Condition Detected! Data is corrupted.");
        }

        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream("bank_data.ser"))) {
            oos.writeObject(Bank.getInstance());
            System.out.println("Test state successfully saved to disk for UI application!");
        } catch (Exception e) {
            System.err.println("Failed to save test state: " + e.getMessage());
        }
    }
}