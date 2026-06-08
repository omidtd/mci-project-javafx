package com.example.javafxtest2.view;

import com.example.javafxtest2.controller.OpenAccountController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OpenAccountView extends VBox {

    private final OpenAccountController controller;

    public OpenAccountView(Stage stage, Scene mainScene) {
        this.controller = new OpenAccountController();

        setSpacing(15);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #ffffff;");

        Label lblHeader = new Label("Open New Bank Account");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField txtNationalId = new TextField();
        txtNationalId.setPromptText("Owner National ID");
        txtNationalId.setMaxWidth(250);

        TextField txtInitialBalance = new TextField();
        txtInitialBalance.setPromptText("Initial Balance");
        txtInitialBalance.setMaxWidth(250);

        TextField txtParam = new TextField();
        txtParam.setPromptText("Interest Rate (e.g. 0.05) / Monthly Fee");
        txtParam.setMaxWidth(250);

        Button btnSavings = new Button("Open Savings Account");
        Button btnChecking = new Button("Open Checking Account");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSavings.setPrefWidth(220);
        btnChecking.setPrefWidth(220);
        btnBack.setPrefWidth(220);

        btnSavings.setOnAction(e -> {
            String response = controller.handleOpenSavingsAccount(
                    txtNationalId.getText(),
                    txtInitialBalance.getText(),
                    txtParam.getText()
            );
            lblMessage.setText(response);
            if (response.startsWith("SUCCESS")) {
                lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                txtNationalId.clear();
                txtInitialBalance.clear();
                txtParam.clear();
            } else {
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnChecking.setOnAction(e -> {
            String response = controller.handleOpenCheckingAccount(
                    txtNationalId.getText(),
                    txtInitialBalance.getText(),
                    txtParam.getText()
            );
            lblMessage.setText(response);
            if (response.startsWith("SUCCESS")) {
                lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                txtNationalId.clear();
                txtInitialBalance.clear();
                txtParam.clear();
            } else {
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));


        getChildren().addAll(lblHeader, txtNationalId,
                txtInitialBalance, txtParam, btnSavings, btnChecking, lblMessage,
                btnBack);
    }
}
