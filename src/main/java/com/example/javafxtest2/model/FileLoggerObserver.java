package com.example.javafxtest2.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLoggerObserver implements TransactionObserver {

    private static final Logger logger = Logger.getLogger(FileLoggerObserver.class.getName());

    public FileLoggerObserver() {
        try {
            FileHandler fileHandler = new FileHandler("transactions.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Could not initialize file logger: " + e.getMessage());
        }
    }

    @Override
    public void onTransactionRegistered(Transaction transaction) {
        try {
            String logMessage = String.format(
                    "Timestamp: %s | TxID: %s | Type: %s | Amount: %s | Src: %s | Dest: %s | Status: %s | Description: %s",
                    transaction.getTimestamp(),
                    transaction.getTransactionId(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getSourceAccountNumber() != null ? transaction.getSourceAccountNumber() : "N/A",
                    transaction.getDestinationAccountNumber() != null ? transaction.getDestinationAccountNumber() : "N/A",
                    transaction.getStatus(),
                    transaction.getDescription()
            );

            if (transaction.getStatus() == com.example.javafxtest2.model.enums.TransactionStatus.SUCCESS) {
                logger.log(Level.INFO, logMessage);
            } else {
                logger.log(Level.WARNING, logMessage);
            }
        } catch (Exception ex) {
            System.err.println("File logger observer failed: " + ex.getMessage());
        }
    }
}