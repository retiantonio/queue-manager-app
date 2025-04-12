package com.example.queuemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField loginPasswordTextField;
    @FXML private TextField loginUsernameTextField;

    @FXML
    private void login(ActionEvent event) {
        if(loginUsernameTextField.getText().equals("retiantonio1") || loginPasswordTextField.getText().equals("12345678")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-app.fxml"));

            try {
                Parent root = loader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //input mismatch
        }
    }
}
