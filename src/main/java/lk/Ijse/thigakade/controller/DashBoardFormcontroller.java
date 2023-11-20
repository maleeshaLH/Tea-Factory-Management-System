package lk.Ijse.thigakade.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormcontroller {

    @FXML
    public AnchorPane root;

    @FXML
    public void btnCustomeronAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("customer Form");
        stage.centerOnScreen();



    }

    public void btnOrderonAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/order_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Order Form");
        stage.centerOnScreen();


    }

    public void btnEmployeeonAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Employee Form");
        stage.centerOnScreen();


    }

    public void btnStockonAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/stock_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("stock Form");
        stage.centerOnScreen();


    }

    public void btnSalaryonAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/progress_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("progress Form");
        stage.centerOnScreen();





    }

    public void btnSupplieronAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("supplier Form");
        stage.centerOnScreen();



    }
}
