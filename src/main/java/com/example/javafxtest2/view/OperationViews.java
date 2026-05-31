package com.example.javafxtest2.view;

import com.example.javafxtest2.model.Bank;
import com.example.javafxtest2.model.CheckingAccount;
import com.example.javafxtest2.model.Customer;
import com.example.javafxtest2.model.SavingsAccount;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.UUID;

public class OperationViews {

    public static Scene getCreateCustomerScene(Stage stage, Scene mainScene) {
        VBox root = createBaseLayout();

        Label lblHeader = new Label("Create New Customer");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");
        TextField txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        TextField txtNationalId = new TextField();
        txtNationalId.setPromptText("National ID");

        Button btnSubmit = new Button("Submit");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSubmit.setPrefWidth(150);
        btnBack.setPrefWidth(150);

        btnSubmit.setOnAction(e -> {
            try {
                Bank.getInstance().createCustomer(
                        txtFirstName.getText(),
                        txtLastName.getText(),
                        txtNationalId.getText()
                );
                lblMessage.setText("Customer created successfully.");
                lblMessage.setStyle("-fx-text-fill: green;");
                txtFirstName.clear();
                txtLastName.clear();
                txtNationalId.clear();
            } catch (Exception ex) {
                lblMessage.setText("Error: " + ex.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        root.getChildren().addAll(lblHeader, txtFirstName, txtLastName, txtNationalId, btnSubmit, lblMessage, btnBack);
        return new Scene(root, 400, 450);
    }

    public static Scene getOpenAccountScene(Stage stage, Scene mainScene) {
        VBox root = createBaseLayout();

        Label lblHeader = new Label("Open New Account");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField txtNationalId = new TextField();
        txtNationalId.setPromptText("Owner National ID");
        TextField txtInitialBalance = new TextField();
        txtInitialBalance.setPromptText("Initial Balance");
        TextField txtParam = new TextField();
        txtParam.setPromptText("Interest Rate / Maintenance Fee");

        Button btnSavings = new Button("Open Savings Account");
        Button btnChecking = new Button("Open Checking Account");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSavings.setPrefWidth(200);
        btnChecking.setPrefWidth(200);
        btnBack.setPrefWidth(200);

        btnSavings.setOnAction(e -> {
            try {
                Customer owner = Bank.getInstance().getCustomer(txtNationalId.getText());
                String accNumber = UUID.randomUUID().toString().substring(0, 8);
                BigDecimal balance = new BigDecimal(txtInitialBalance.getText());
                BigDecimal rate = new BigDecimal(txtParam.getText());

                SavingsAccount account = new SavingsAccount(accNumber, owner, balance, rate);
                Bank.getInstance().addAccount(account);

                lblMessage.setText("Savings Account created: " + accNumber);
                lblMessage.setStyle("-fx-text-fill: green;");
            } catch (Exception ex) {
                lblMessage.setText("Error: " + ex.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        });

        btnChecking.setOnAction(e -> {
            try {
                Customer owner = Bank.getInstance().getCustomer(txtNationalId.getText());
                String accNumber = UUID.randomUUID().toString().substring(0, 8);
                BigDecimal balance = new BigDecimal(txtInitialBalance.getText());
                BigDecimal fee = new BigDecimal(txtParam.getText());

                CheckingAccount account = new CheckingAccount(accNumber, owner, balance, new BigDecimal("1000"), fee);
                Bank.getInstance().addAccount(account);

                lblMessage.setText("Checking Account created: " + accNumber);
                lblMessage.setStyle("-fx-text-fill: green;");
            } catch (Exception ex) {
                lblMessage.setText("Error: " + ex.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        root.getChildren().addAll(lblHeader, txtNationalId, txtInitialBalance, txtParam, btnSavings, btnChecking, lblMessage, btnBack);
        return new Scene(root, 400, 450);
    }

    public static Scene getDepositScene(Stage stage, Scene mainScene) {
        VBox root = createBaseLayout();

        Label lblHeader = new Label("Deposit Funds");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField txtAccountId = new TextField();
        txtAccountId.setPromptText("Account Number");
        TextField txtAmount = new TextField();
        txtAmount.setPromptText("Amount");

        Button btnSubmit = new Button("Deposit");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSubmit.setPrefWidth(150);
        btnBack.setPrefWidth(150);

        btnSubmit.setOnAction(e -> {
            try {
                BigDecimal amount = new BigDecimal(txtAmount.getText());
                Bank.getInstance().getAccount(txtAccountId.getText()).deposit(amount);
                lblMessage.setText("Deposit successful.");
                lblMessage.setStyle("-fx-text-fill: green;");
                txtAccountId.clear();
                txtAmount.clear();
            } catch (Exception ex) {
                lblMessage.setText("Error: " + ex.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        root.getChildren().addAll(lblHeader, txtAccountId, txtAmount, btnSubmit, lblMessage, btnBack);
        return new Scene(root, 400, 450);
    }

    public static Scene getWithdrawScene(Stage stage, Scene mainScene) {
        VBox root = createBaseLayout();

        Label lblHeader = new Label("Withdraw Funds");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField txtAccountId = new TextField();
        txtAccountId.setPromptText("Account Number");
        TextField txtAmount = new TextField();
        txtAmount.setPromptText("Amount");

        Button btnSubmit = new Button("Withdraw");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSubmit.setPrefWidth(150);
        btnBack.setPrefWidth(150);

        btnSubmit.setOnAction(e -> {
            try {
                BigDecimal amount = new BigDecimal(txtAmount.getText());
                Bank.getInstance().getAccount(txtAccountId.getText()).withdraw(amount);
                lblMessage.setText("Withdrawal successful.");
                lblMessage.setStyle("-fx-text-fill: green;");
                txtAccountId.clear();
                txtAmount.clear();
            } catch (Exception ex) {
                lblMessage.setText("Error: " + ex.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        root.getChildren().addAll(lblHeader, txtAccountId, txtAmount, btnSubmit, lblMessage, btnBack);
        return new Scene(root, 400, 450);
    }

    public static Scene getTransferScene(Stage stage, Scene mainScene) {
        VBox root = createBaseLayout();

        Label lblHeader = new Label("Transfer Funds");
        lblHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField txtSourceId = new TextField();
        txtSourceId.setPromptText("Source Account Number");
        TextField txtDestId = new TextField();
        txtDestId.setPromptText("Destination Account Number");
        TextField txtAmount = new TextField();
        txtAmount.setPromptText("Amount");

        Button btnSubmit = new Button("Transfer");
        Button btnBack = new Button("Back to Menu");
        Label lblMessage = new Label();

        btnSubmit.setPrefWidth(150);
        btnBack.setPrefWidth(150);

        btnSubmit.setOnAction(e -> {
            try {
                BigDecimal amount = new BigDecimal(txtAmount.getText());
                Bank.getInstance().transfer(txtSourceId.getText(), txtDestId.getText(), amount);
                lblMessage.setText("Transfer completed successfully.");
                lblMessage.setStyle("-fx-text-fill: green;");
                txtSourceId.clear();
                txtDestId.clear();
                txtAmount.clear();
            } catch (Exception ex) {
                lblMessage.setText("Error: " + ex.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        });

        btnBack.setOnAction(e -> stage.setScene(mainScene));

        root.getChildren().addAll(lblHeader, txtSourceId, txtDestId, txtAmount, btnSubmit, lblMessage, btnBack);
        return new Scene(root, 400, 450);
    }

    private static VBox createBaseLayout() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #ffffff;");
        return root;
    }
}