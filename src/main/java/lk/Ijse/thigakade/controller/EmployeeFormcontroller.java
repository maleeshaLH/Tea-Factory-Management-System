package lk.Ijse.thigakade.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.Ijse.thigakade.dto.EmployeeDto;
import lk.Ijse.thigakade.dto.tm.EmployeeTm;
import lk.Ijse.thigakade.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
public class EmployeeFormcontroller {

    @FXML
    public Pane root;
    @FXML
    public TableView <EmployeeTm>tblEmployee;

    @FXML
    private TextField txttel;

    @FXML
    private TableColumn<?, ?> colcity;

    @FXML
    private TableColumn<?, ?> colfirst_name;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> collast_name;

    @FXML
    private TableColumn<?, ?> colnic;

    @FXML
    private TableColumn<?, ?> coltel;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtEmployee_id;

    @FXML
    private TextField txtFirst_name;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txttLast_name;

    @FXML
    void btnClearOnAction(ActionEvent event) {clesrFields(); }



    @FXML
    void btnDalateOnAction(ActionEvent event) {



    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Emp_id = txtEmployee_id.getText();
        String emp_firstname = txtFirst_name.getText();
        String emp_last_name = txttLast_name.getText();
        String nic = txtNic.getText();
        String city = txtCity.getText();
        String tel = txttel.getText();

        var dto = new EmployeeDto(Emp_id,emp_firstname,emp_last_name,nic,city,tel);

        var model = new EmployeeModel();

        try {
            boolean isSaved = model.saveEmployee(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved customer").show();
                clesrFields();

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
         
    private void clesrFields() {
        txtEmployee_id.setText("");
        txtFirst_name.setText("");
        txttLast_name.setText("");
        txtNic.setText("");
        txtCity.setText("");
        txttel.setText("");

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String emp_id = txtEmployee_id.getText();
        String emp_firstname = txtFirst_name.getText();
        String emp_lastname = txttLast_name.getText();
        String nic = txtNic.getText();
        String city = txtCity.getText();
        String tel = txttel.getText();

        var dto = new EmployeeDto(emp_id,emp_firstname,emp_lastname,nic,city,tel);

        var model = new EmployeeModel();

        try {
            boolean isUpdate = model.updateEmployee(dto);
            System.out.println(isUpdate);

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"employee update").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();         }

    }

    @FXML
    void btnbackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashBoard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("DashBoard");
        stage.centerOnScreen();

    }

    @FXML
    void txtsearchOnAction(ActionEvent event) {
        String emp_id = txtEmployee_id.getText();

        var model = new EmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(emp_id);
            if (dto != null){
                fillFielde(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"employee not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void fillFielde(EmployeeDto dto) {
        txtEmployee_id.setText(dto.getEmp_id());
        txtFirst_name.setText(dto.getFirst_name());
        txttLast_name.setText(dto.getLast_name());
        txtNic.setText(dto.getNic());
        txtCity.setText(dto.getCity());
        txttel.setText(dto.getTel());
    }

}
