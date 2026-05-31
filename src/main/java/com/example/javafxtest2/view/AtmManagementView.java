package com.example.javafxtest2.view;

import com.example.javafxtest2.controller.AtmController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AtmManagementView extends VBox {

    private final AtmController controller;

    public AtmManagementView(Stage stage, Scene mainScene) {
        this.controller = new AtmController();

        setSpacing(15);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #ffffff;");

        Label lblHeader = new Label("ATM Network Simulation");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField txtAtmCount = new TextField();
        txtAtmCount.setPromptText("Enter Number of ATMs (e.g. 5)");
        txtAtmCount.setMaxWidth(250);

        Button btnStart = new Button("Start ATM Simulation");
        Button btnStop = new Button("Stop All ATMs");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnStart.setPrefWidth(200);
        btnStop.setPrefWidth(200);
        btnBack.setPrefWidth(200);

        btnStart.setOnAction(e -> {
            String countText = txtAtmCount.getText();
            if (countText == null || countText.trim().isEmpty()) {
                lblMessage.setText("ERROR: Please enter a number.");
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                return;
            }
            try {
                int count = Integer.parseInt(countText.trim());
                if (count <= 0) {
                    lblMessage.setText("ERROR: Count must be greater than zero.");
                    lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    return;
                }
                controller.startAtms(count);
                lblMessage.setText("SUCCESS: Started " + count + " ATMs in background.");
                lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } catch (NumberFormatException ex) {
                lblMessage.setText("ERROR: Invalid number format.");
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnStop.setOnAction(e -> {
            controller.stopAllAtms();
            lblMessage.setText("SUCCESS: All background ATMs stopped.");
            lblMessage.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
            txtAtmCount.clear();
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        getChildren().addAll(lblHeader, txtAtmCount, btnStart, btnStop, lblMessage, btnBack);
    }
}