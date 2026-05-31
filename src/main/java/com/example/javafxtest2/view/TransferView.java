package com.example.javafxtest2.view;

import com.example.javafxtest2.controller.TransferController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransferView extends VBox {

    private final TransferController controller;

    public TransferView(Stage stage, Scene mainScene) {
        this.controller = new TransferController();

        setSpacing(15);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #ffffff;");

        Label lblHeader = new Label("Transfer Funds");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField txtSourceId = new TextField();
        txtSourceId.setPromptText("Source Account Number");
        txtSourceId.setMaxWidth(250);

        TextField txtDestId = new TextField();
        txtDestId.setPromptText("Destination Account Number");
        txtDestId.setMaxWidth(250);

        TextField txtAmount = new TextField();
        txtAmount.setPromptText("Amount");
        txtAmount.setMaxWidth(250);

        Button btnSubmit = new Button("Transfer");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSubmit.setPrefWidth(150);
        btnBack.setPrefWidth(150);

        btnSubmit.setOnAction(e -> {
            String response = controller.handleTransfer(
                    txtSourceId.getText(),
                    txtDestId.getText(),
                    txtAmount.getText()
            );
            lblMessage.setText(response);
            if (response.startsWith("SUCCESS")) {
                lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                txtSourceId.clear();
                txtDestId.clear();
                txtAmount.clear();
            } else {
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        getChildren().addAll(lblHeader, txtSourceId, txtDestId, txtAmount, btnSubmit, lblMessage, btnBack);
    }
}