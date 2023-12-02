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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.Ijse.thigakade.dto.CustomerDto;
import lk.Ijse.thigakade.dto.tm.CustomerTm;
import lk.Ijse.thigakade.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;


public class CustomerFormcontroller {

    @FXML
    public Pane root;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colFirst_name;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLast_name;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtFirst_name;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLast_name;

    @FXML
    private TextField txtcity;

    @FXML
    private TextField txttel;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirst_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        colLast_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
    }

    private void loadAllCustomers() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = model.getAllCustomers();

            for (CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getFirst_name(),
                                dto.getLast_name(),
                                dto.getTel(),
                                dto.getAddress(),
                                dto.getCity()
                        )
                );
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private boolean validateCustomer() {
        String id = txtId.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isCustomerIDValidated = Pattern.matches("[c][0-9]{2,}", id);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
        return false;
        }
        return true;

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        var customerModel = new CustomerModel();
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);

            if(isDeleted) {
                tblCustomer.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event)  {
        boolean isCustomerIdValidated = validateCustomer();

        if (isCustomerIdValidated){
            String id = txtId.getText();
            String first_name = txtFirst_name.getText();
            String last_name =txtLast_name.getText();
            String tel = txttel.getText();
            String address = txtAddress.getText();
            String city = txtcity.getText();


            var dto = new CustomerDto(id,first_name,last_name,tel,address,city);

            var model = new CustomerModel();
            try {
                boolean isSaved = model.saveCustomer(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    clesrFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


    }

    private void clesrFields() {
        txtFirst_name.setText("");
        txtLast_name.setText("");
        txtId.setText("");
        txtAddress.setText("");
        txtcity.setText("");
        txttel.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String first_name = txtFirst_name.getText();
        String last_name =txtLast_name.getText();
        String tel = txttel.getText();
        String address = txtAddress.getText();
        String city = txtcity.getText();

        var dto = new CustomerDto(id,first_name,last_name,tel,address,city);

        var model = new CustomerModel();
        try {
            boolean isUpdated = model.updateCustomer(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new CustomerModel();
        try {
            CustomerDto dto = model.searchCustomer(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void fillFields(CustomerDto dto) {
        txtId.setText(dto.getId());
        txtFirst_name.setText(dto.getFirst_name());
        txtLast_name.setText(dto.getLast_name());
        txttel.setText(dto.getTel());
        txtAddress.setText(dto.getAddress());
        txtcity.setText(dto.getCity());
    }


    public void btnbackOnAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"))));
        stage.setTitle("dashboard Form");
        stage.centerOnScreen();

        stage.show();
    }

    public void btnClearOnAction(ActionEvent event) {
        clesrFields();
    }
}
