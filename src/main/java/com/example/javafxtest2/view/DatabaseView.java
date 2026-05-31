package com.example.javafxtest2.view;

import com.example.javafxtest2.controller.DatabaseController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DatabaseView extends VBox {

    private final DatabaseController controller;

    public DatabaseView(Stage stage, Scene mainScene) {
        this.controller = new DatabaseController();

        setSpacing(15);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #ffffff;");

        Label lblHeader = new Label("Backup & Restore Database");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Button btnSave = new Button("Save State to Disk");
        Button btnLoad = new Button("Load State from Disk");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSave.setPrefWidth(200);
        btnLoad.setPrefWidth(200);
        btnBack.setPrefWidth(200);

        btnSave.setOnAction(e -> {
            String response = controller.saveData();
            lblMessage.setText(response);
            if (response.startsWith("SUCCESS")) {
                lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnLoad.setOnAction(e -> {
            String response = controller.loadData();
            lblMessage.setText(response);
            if (response.startsWith("SUCCESS")) {
                lblMessage.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
            } else {
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        getChildren().addAll(lblHeader, btnSave, btnLoad, lblMessage, btnBack);
    }
}