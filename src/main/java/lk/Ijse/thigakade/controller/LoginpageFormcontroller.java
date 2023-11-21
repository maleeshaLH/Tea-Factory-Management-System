package lk.Ijse.thigakade.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.Ijse.thigakade.model.UserModel;

import java.io.IOException;

public class LoginpageFormcontroller {

    @FXML
    private JFXButton btnlogin;

    @FXML
    private JFXButton btnsigiUp1;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField txtusername;
    private UserModel userModel=new UserModel();


    @FXML
    void btnloginOnAction(ActionEvent event) throws IOException {
        String username = txtusername.getText();
        String password = txtpassword.getText();

        if (UserModel.saveUser(username,password)){
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashBoard_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("dashboard Manage");
            stage.centerOnScreen();

        }
           // String dto = userModel.searchUser(username,password);



    }

    @FXML
    void btnsignUpOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Signup Manage");
        stage.centerOnScreen();

    }


}

