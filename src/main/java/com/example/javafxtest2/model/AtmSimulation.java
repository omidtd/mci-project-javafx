package com.example.javafxtest2.model;

import com.example.javafxtest2.model.enums.TransactionStatus;
import com.example.javafxtest2.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class AtmSimulation implements Runnable {

    private final String atmId;
    private final Random random;
    private volatile boolean running;

    public AtmSimulation(String atmId) {
        this.atmId = atmId;
        this.random = new Random();
        this.running = true;
    }

    public void stopSimulation() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000 + random.nextInt(2000));

                List<Account> allAccounts = Bank.getInstance().getAllAccounts();
                if (allAccounts.size() < 2) {
                    continue;
                }

                Account source = allAccounts.get(random.nextInt(allAccounts.size()));
                Account destination = allAccounts.get(random.nextInt(allAccounts.size()));
                BigDecimal amount = new BigDecimal(1000 + random.nextInt(5000));

                int operationType = random.nextInt(3);

                if (operationType == 0) {
                    source.deposit(amount);
                    generateLog(TransactionType.DEPOSIT, amount, null, source.getAccountNumber());
                } else if (operationType == 1) {
                    try {
                        source.withdraw(amount);
                        generateLog(TransactionType.WITHDRAWAL, amount, source.getAccountNumber(), null);
                    } catch (Exception ignored) {}
                } else {
                    try {
                        Bank.getInstance().transfer(source.getAccountNumber(), destination.getAccountNumber(), amount);
                        generateLog(TransactionType.TRANSFER, amount, source.getAccountNumber(), destination.getAccountNumber());
                    } catch (Exception ignored) {}
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void generateLog(TransactionType type, BigDecimal amount, String src, String dest) {
        String txId = UUID.randomUUID().toString().substring(0, 8);
        Transaction tx = new Transaction(
                txId, type, amount, src, dest,
                TransactionStatus.SUCCESS,
                "Automated transaction by " + atmId,
                LocalDateTime.now()
        );
        System.out.println("[" + atmId + "] " + type + " | Amount: " + amount + " | Tx: " + txId + " | Status: SUCCESS");
    }
}