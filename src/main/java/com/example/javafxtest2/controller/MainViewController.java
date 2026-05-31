package com.example.javafxtest2.controller;

import com.example.javafxtest2.view.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainViewController {

    private final Stage stage;

    public MainViewController(Stage stage) {
        this.stage = stage;
    }

    public void navigateToCreateCustomer(Scene mainScene) {
        stage.setScene(new Scene(new CreateCustomerView(stage, mainScene), 400, 450));
    }

    public void navigateToOpenAccount(Scene mainScene) {
        stage.setScene(new Scene(new OpenAccountView(stage, mainScene), 400, 450));
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