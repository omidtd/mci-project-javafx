package com.example.javafxtest2.controller;

import com.example.javafxtest2.model.Bank;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DatabaseController {

    private static final String FILE_NAME = "bank_data.ser";

    public synchronized String saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(Bank.getInstance());
            return "SUCCESS: Bank data saved successfully.";
        } catch (Exception ex) {
            return "ERROR: Failed to save data: " + ex.getMessage();
        }
    }

    public synchronized String loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Bank loadedBank = (Bank) ois.readObject();
            Bank.setInstance(loadedBank);
            return "SUCCESS: Bank data restored successfully.";
        } catch (Exception ex) {
            return "ERROR: Failed to load data: " + ex.getMessage();
        }
    }
}