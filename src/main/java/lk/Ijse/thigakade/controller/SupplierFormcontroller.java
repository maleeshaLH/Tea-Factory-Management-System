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
import lk.Ijse.thigakade.dto.SupplierDto;
import lk.Ijse.thigakade.dto.tm.SupplierTm;
import lk.Ijse.thigakade.model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormcontroller {
    @FXML
    public AnchorPane root;

    @FXML
    public TableView tblSupplier;
    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colS_id;

    @FXML
    private TableColumn<?, ?> colemail;

    @FXML
    private TableColumn<?, ?> coltel;

    @FXML
    private TextField txtS_id;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtlocation;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txttel;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colS_id.setCellValueFactory(new PropertyValueFactory<>("s_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("s_name"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("s_location"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("s_email"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("s_tel"));
    }

    private void loadAllCustomers() {
        var model = new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = model.getAllSupplier();

            for (SupplierDto dto : dtoList) {
                obList.add(
                        new SupplierTm(
                                dto.getS_id(),
                                dto.getS_name(),
                                dto.getS_location(),
                                dto.getS_email(),
                                dto.getS_tel()
                        )
                );
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
   public void btnClearOnAction(ActionEvent event) { clesrFields(); }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtS_id.getText();

        var supplierModel =  new SupplierModel();
        try {
            boolean isDelete = supplierModel.deleteSupplier(id);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"supplier delete").show();


            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    @FXML
   public void btnSaveOnAction(ActionEvent event) {
        String s_id= txtS_id.getText();
        String name=txtname.getText();
        String location= txtlocation.getText();
        String email = txtemail.getText();
        String tel = txttel.getText();

        var dto = new SupplierDto(s_id,name,location,email,tel);

        var model = new SupplierModel();

        try {
            boolean isSaved = model.saveSupplier(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved suppplier").show();
                clesrFields();

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void clesrFields() {
        txtS_id.setText("");
        txtname.setText("");
        txtlocation.setText("");
        txtemail.setText("");
        txttel.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String s_id= txtS_id.getText();
        String name=txtname.getText();
        String location= txtlocation.getText();
        String email = txtemail.getText();
        String tel = txttel.getText();


        var dto = new SupplierDto(s_id,name,location,email,tel);

        var model = new SupplierModel();

        try {
            boolean isUpdate = model.updateSupplier(dto);
            System.out.println(isUpdate);

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"update suppplier").show();

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void txtsearchOnAction(ActionEvent event) {
        String id = txtS_id.getText();

        var model = new SupplierModel();
        try {
            SupplierDto dto = model.searchSupplier(id);
            if (dto != null){
                fillFielde(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"search not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void fillFielde(SupplierDto dto) {
        txtS_id.setText(dto.getS_id());
        txtname.setText(dto.getS_name());
        txtlocation.setText(dto.getS_location());
        txtemail.setText(dto.getS_email());
        txttel.setText(dto.getS_tel());

    }

}
