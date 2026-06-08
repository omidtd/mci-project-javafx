package com.example.javafxtest2.view;

import com.example.javafxtest2.controller.BalanceController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BalanceView extends VBox {

    private final BalanceController controller;

    public BalanceView(Stage stage, Scene mainScene) {
        this.controller = new BalanceController();

        setSpacing(15);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #ffffff;");

        Label lblHeader = new Label("Check Account Balance");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField txtAccountNumber = new TextField();
        txtAccountNumber.setPromptText("Account Number");
        txtAccountNumber.setMaxWidth(250);

        Button btnSubmit = new Button("Submit");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSubmit.setPrefWidth(150);
        btnBack.setPrefWidth(150);

        btnSubmit.setOnAction(e -> {
            String response = controller.handleGetBalance(txtAccountNumber.getText());

            lblMessage.setText(response);
            if (response.startsWith("SUCCESS")) {
                lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                txtAccountNumber.clear();
            } else {
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        getChildren().addAll(lblHeader, txtAccountNumber, btnSubmit, lblMessage, btnBack);
    }
}