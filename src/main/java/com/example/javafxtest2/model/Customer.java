package com.example.javafxtest2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;
    private final String nationalId;
    private final List<Account> accounts;

    public Customer(String firstName, String lastName, String nationalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.accounts = Collections.synchronizedList(new ArrayList<>());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}