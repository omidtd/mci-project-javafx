package com.example.javafxtest2.view;

import com.example.javafxtest2.controller.MainViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends VBox {

    private final MainViewController controller;
    private Scene mainScene;

    public MainView(Stage stage) {
        this.controller = new MainViewController(stage);

        setSpacing(15);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #f4f4f4;");

        Label lblTitle = new Label("Bank");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Button btnCreateCustomer = new Button("Create Customer");
        Button btnOpenAccount = new Button("Open Account");
        Button btnDeposit = new Button("Deposit");
        Button btnWithdraw = new Button("Withdraw");
        Button btnTransfer = new Button("Transfer");
        Button btnAtmSim = new Button("ATM Simulation");
        Button btnDatabase = new Button("Backup & Restore");

        double buttonWidth = 220;
        btnCreateCustomer.setPrefWidth(buttonWidth);
        btnOpenAccount.setPrefWidth(buttonWidth);
        btnDeposit.setPrefWidth(buttonWidth);
        btnWithdraw.setPrefWidth(buttonWidth);
        btnTransfer.setPrefWidth(buttonWidth);
        btnAtmSim.setPrefWidth(buttonWidth);
        btnDatabase.setPrefWidth(buttonWidth);

        String buttonStyle = "-fx-font-size: 14px; -fx-padding: 8px;";
        btnCreateCustomer.setStyle(buttonStyle);
        btnOpenAccount.setStyle(buttonStyle);
        btnDeposit.setStyle(buttonStyle);
        btnWithdraw.setStyle(buttonStyle);
        btnTransfer.setStyle(buttonStyle);
        btnAtmSim.setStyle(buttonStyle);
        btnDatabase.setStyle(buttonStyle);

        btnCreateCustomer.setOnAction(e -> controller.navigateToCreateCustomer(mainScene));
        btnOpenAccount.setOnAction(e -> controller.navigateToOpenAccount(mainScene));
        btnDeposit.setOnAction(e -> controller.navigateToDeposit(mainScene));
        btnWithdraw.setOnAction(e -> controller.navigateToWithdraw(mainScene));
        btnTransfer.setOnAction(e -> controller.navigateToTransfer(mainScene));
        btnAtmSim.setOnAction(e -> controller.navigateToAtmSimulation(mainScene));
        btnDatabase.setOnAction(e -> controller.navigateToDatabase(mainScene));

        getChildren().addAll(
                lblTitle,
                btnCreateCustomer,
                btnOpenAccount,
                btnDeposit,
                btnWithdraw,
                btnTransfer,
                btnAtmSim,
                btnDatabase
        );
    }

    public Scene createAndGetScene() {
        this.mainScene = new Scene(this, 400, 450);
        return this.mainScene;
    }
}