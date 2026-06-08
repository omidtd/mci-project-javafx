package com.example.javafxtest2.controller;

import com.example.javafxtest2.model.Account;
import com.example.javafxtest2.model.Bank;
import com.example.javafxtest2.model.TimeObserver;
import com.example.javafxtest2.view.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDate;

public class MainViewController {

    private final Stage stage;

    public MainViewController(Stage stage) {
        this.stage = stage;
    }

    public String getCurrentDateString() {
        return Bank.getInstance().getVirtualDate().toString();
    }

    public String handleNextDay() {
        Bank bank = Bank.getInstance();
        LocalDate oldDate = bank.getVirtualDate();
        LocalDate newDate = oldDate.plusDays(1);
        bank.setVirtualDate(newDate);

        boolean monthChanged = (oldDate.getMonthValue() != newDate.getMonthValue());

        for (Account account : bank.getAllAccounts()) {
            if (account instanceof TimeObserver) {
                ((TimeObserver) account).onDateChanged(newDate, monthChanged);
            }
        }

        return newDate.toString();
    }

    public void navigateToCreateCustomer(Scene mainScene) {
        stage.setScene(new Scene(new CreateCustomerView(stage, mainScene), 400, 450));
    }

    public void navigateToOpenAccount(Scene mainScene) {
        stage.setScene(new Scene(new OpenAccountView(stage, mainScene), 400, 450));
    }

    public void navigateToShowBalance(Scene mainScene) {
        stage.setScene(new Scene(new BalanceView(stage, mainScene), 400, 450));
    }

    public void navigateToDeposit(Scene mainScene) {
        stage.setScene(new Scene(new DepositView(stage, mainScene), 400, 450));
    }

    public void navigateToWithdraw(Scene mainScene) {
        stage.setScene(new Scene(new WithdrawView(stage, mainScene), 400, 450));
    }

    public void navigateToTransfer(Scene mainScene) {
        stage.setScene(new Scene(new TransferView(stage, mainScene), 400, 450));
    }

    public void navigateToAtmSimulation(Scene mainScene) {
        stage.setScene(new Scene(new AtmManagementView(stage, mainScene), 400, 450));
    }

    public void navigateToDatabase(Scene mainScene) {
        stage.setScene(new Scene(new DatabaseView(stage, mainScene), 400, 450));
    }
}