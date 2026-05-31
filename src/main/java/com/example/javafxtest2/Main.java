package com.example.javafxtest2;

import com.example.javafxtest2.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView(primaryStage);
        primaryStage.setTitle("Concurrent Banking System");
        primaryStage.setScene(mainView.createAndGetScene());
        primaryStage.show();
    }
}