package com.example.javafxtest2.view;

import com.example.javafxtest2.controller.DepositController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DepositView extends VBox {

    private final DepositController controller;

    public DepositView(Stage stage, Scene mainScene) {
        this.controller = new DepositController();

        setSpacing(15);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #ffffff;");

        Label lblHeader = new Label("Deposit Funds");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField txtAccountId = new TextField();
        txtAccountId.setPromptText("Account Number");
        txtAccountId.setMaxWidth(250);

        TextField txtAmount = new TextField();
        txtAmount.setPromptText("Amount");
        txtAmount.setMaxWidth(250);

        Button btnSubmit = new Button("Deposit");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSubmit.setPrefWidth(150);
        btnBack.setPrefWidth(150);

        btnSubmit.setOnAction(e -> {
            String response = controller.handleDeposit(txtAccountId.getText(), txtAmount.getText());
            lblMessage.setText(response);
            if (response.startsWith("SUCCESS")) {
                lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                txtAccountId.clear();
                txtAmount.clear();
            } else {
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        getChildren().addAll(lblHeader, txtAccountId, txtAmount, btnSubmit, lblMessage, btnBack);
    }
}