package com.example.javafxtest2.controller;

import com.example.javafxtest2.model.Bank;

public class CreateCustomerController {

    public String handleCreateCustomer(String firstName, String lastName, String nationalId) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return "ERROR: First name cannot be empty.";
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return "ERROR: Last name cannot be empty.";
        }
        if (nationalId == null || nationalId.trim().isEmpty()) {
            return "ERROR: National ID cannot be empty.";
        }
        if (!nationalId.matches("\\d+")) {
            return "ERROR: National ID must contain only digits.";
        }

        try {
            Bank.getInstance().createCustomer(firstName.trim(), lastName.trim(), nationalId.trim());
            return "SUCCESS: Customer created successfully.";
        } catch (Exception ex) {
            return "ERROR: " + ex.getMessage();
        }
    }
}