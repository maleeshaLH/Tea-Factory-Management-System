package lk.Ijse.thigakade.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.Ijse.thigakade.dto.EmployeeDto;
import lk.Ijse.thigakade.dto.tm.EmployeeTm;
import lk.Ijse.thigakade.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormcontroller {

    @FXML
    private TableColumn<?, ?> clocity;


    @FXML
    private TableColumn<?, ?> colEId;

    @FXML
    private TableColumn<?, ?> colFirst_name;

    @FXML
    private TableColumn<?, ?> colLast_name;

    @FXML
    private TableColumn<?, ?> coltel;

    @FXML
    private TableColumn<?, ?> colnic;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtEmployeeid;

    @FXML
    private TextField txtFirst_name;

    @FXML
    private TextField txtLast_name;

    @FXML
    private TextField txtcity;

    @FXML
    private TextField txtnic;

    @FXML
    private TextField txttel;

    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
    }

    private void setCellValueFactory() {
        colEId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colFirst_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        colLast_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        colnic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        clocity.setCellValueFactory(new PropertyValueFactory<>("city"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllEmployee() {
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = model.getAllEmployee();

            for (EmployeeDto dto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                dto.getEmp_id(),
                                dto.getFirst_name(),
                                dto.getLast_name(),
                                dto.getNic(),
                                dto.getCity(),
                                dto.getTel()
                        )
                );
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmployeeid.getText();

        var employeeModel = new EmployeeModel();
        try {
            boolean isDeleted = employeeModel.deleteEmployee(id);

            if(isDeleted) {
                tblEmployee.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtEmployeeid.getText();
        String first_name = txtFirst_name.getText();
        String last_name =txtLast_name.getText();
        String nic = txtnic.getText();
        String city = txtcity.getText();
        String tel = txttel.getText();

        var dto = new EmployeeDto(id, first_name,last_name,nic, city, tel);

        var model = new EmployeeModel();
        try {
            boolean isSaved = model.saveEmployee(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clearFields() {
        txtFirst_name.setText("");
        txtLast_name.setText("");
        txtEmployeeid.setText("");
        txtnic.setText("");
        txtcity.setText("");
        txttel.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeid.getText();
        String first_name = txtFirst_name.getText();
        String last_name =txtLast_name.getText();
        String nic = txtnic.getText();
        String city = txtcity.getText();
        String tel = txttel.getText();

        var dto = new EmployeeDto(id,first_name,last_name,nic,city,tel);

        var model = new EmployeeModel();
        try {
            boolean isUpdated = model.updateEmployee(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnbackOnAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"))));
        stage.setTitle("dashboard Form");
        stage.centerOnScreen();

        stage.show();

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtEmployeeid.getText();

        var model = new EmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void fillFields(EmployeeDto dto) {
        txtEmployeeid.setText(dto.getEmp_id());
        txtFirst_name.setText(dto.getFirst_name());
        txtLast_name.setText(dto.getLast_name());
        txtnic.setText(dto.getNic());
        txtcity.setText(dto.getCity());
        txttel.setText(dto.getTel());
    }

}
