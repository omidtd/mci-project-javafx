package com.example.javafxtest2.view;

import com.example.javafxtest2.controller.CreateCustomerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateCustomerView extends VBox {

    private final CreateCustomerController controller;

    public CreateCustomerView(Stage stage, Scene mainScene) {
        this.controller = new CreateCustomerController();

        setSpacing(15);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #ffffff;");

        Label lblHeader = new Label("Create New Customer");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");
        txtFirstName.setMaxWidth(250);

        TextField txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        txtLastName.setMaxWidth(250);

        TextField txtNationalId = new TextField();
        txtNationalId.setPromptText("National ID");
        txtNationalId.setMaxWidth(250);

        Button btnSubmit = new Button("Submit");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSubmit.setPrefWidth(150);
        btnBack.setPrefWidth(150);

        btnSubmit.setOnAction(e -> {
            String response = controller.handleCreateCustomer(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtNationalId.getText()
            );

            lblMessage.setText(response);
            if (response.startsWith("SUCCESS")) {
                lblMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                txtFirstName.clear();
                txtLastName.clear();
                txtNationalId.clear();
            } else {
                lblMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        getChildren().addAll(lblHeader, txtFirstName, txtLastName, txtNationalId, btnSubmit, lblMessage, btnBack);
    }
}