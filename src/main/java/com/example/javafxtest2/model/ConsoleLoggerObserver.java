package com.example.javafxtest2.model;

public class ConsoleLoggerObserver implements TransactionObserver {
    @Override
    public void onTransactionRegistered(Transaction transaction) {
        try {
            System.out.println("\n=== [OBSERVER LOG] ===");
            System.out.println("Tx ID: " + transaction.getTransactionId());
            System.out.println("Type: " + transaction.getType());
            System.out.println("Amount: " + transaction.getAmount());
            System.out.println("Source: " + (transaction.getSourceAccountNumber() != null ? transaction.getSourceAccountNumber() : "N/A"));
            System.out.println("Destination: " + (transaction.getDestinationAccountNumber() != null ? transaction.getDestinationAccountNumber() : "N/A"));
            System.out.println("Status: " + transaction.getStatus());
            System.out.println("Timestamp: " + transaction.getTimestamp());
            System.out.println("=======================\n");
        } catch (Exception ex) {
            System.err.println("Observer failed to log transaction: " + ex.getMessage());
        }
    }
}