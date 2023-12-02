package lk.Ijse.thigakade.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormcontroller {

    @FXML
    public AnchorPane root;
    @FXML
    public AnchorPane DashBoard;

    @FXML
    public void btnCustomeronAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) DashBoard.getScene().getWindow();
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
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/salary_form.fxml")));
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

    public void btnSupplierOrderonAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/supplierOrder_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("supplier Order Form");
        stage.centerOnScreen();
    }

    public void btnLogoutOnAction(ActionEvent event) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout Confirmation");
            alert.setHeaderText("Are you sure you want to log out?");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    Stage stage = (Stage) root.getScene().getWindow();
                    try {
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.centerOnScreen();
                    stage.show();

                } else if (buttonType == buttonTypeNo) {
                    // Do nothing or close the dialog
                    alert.close();
                }
            });


    }
}
