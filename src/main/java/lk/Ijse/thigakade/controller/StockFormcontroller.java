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
import lk.Ijse.thigakade.dto.StockDto;
import lk.Ijse.thigakade.dto.tm.StockTm;
import lk.Ijse.thigakade.model.StockModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class StockFormcontroller {
    @FXML
    public AnchorPane root;
    @FXML
    private TableColumn<?, ?> colproductionid;
    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private TableColumn<?, ?> colweight;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtqty;

    @FXML
    private TextField txtweight;
    @FXML
    private TableView<StockTm> tblStock;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colproductionid.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("p_name"));
        colweight.setCellValueFactory(new PropertyValueFactory<>("p_weight"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("p_qty"));
    }

    private void loadAllCustomers() {
        var model = new StockModel();

        ObservableList<StockTm> obList = FXCollections.observableArrayList();

        try {
            List<StockDto> dtoList = model.getAllStock();

            for (StockDto dto : dtoList) {
                obList.add(
                        new StockTm(
                                dto.getP_id(),
                                dto.getP_name(),
                                dto.getP_weight(),
                                dto.getP_qty()

                        )
                );
            }

            tblStock.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    void btnClearOnAction(ActionEvent event) { clesrFields(); }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        var stockModel =  new StockModel();
        try {
            boolean isDelete = stockModel.deleteStock(id);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"stock delete").show();


            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnsaveeOnAction(ActionEvent event) {
        String p_id= txtId.getText();
        String p_name=txtname.getText();
        String p_weight= txtweight.getText();
        String p_qty = txtqty.getText();

        var dto = new StockDto(p_id,p_name,p_weight,p_qty);

        var model = new StockModel();

        try {
            boolean isSaved = model.saveStock(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved stock").show();
                clesrFields();

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void clesrFields() {
        txtId.setText("");
        txtname.setText("");
        txtweight.setText("");
        txtqty.setText("");


    }

    @FXML
    void btnupdateOnAction(ActionEvent event) {
        String p_id= txtId.getText();
        String p_name=txtname.getText();
        String p_weight= txtweight.getText();
        String p_qty = txtqty.getText();

        var dto = new StockDto(p_id,p_name,p_weight,p_qty);

        var model = new StockModel();

        try {
            boolean isUpdate = model.updateStock(dto);
            System.out.println(isUpdate);

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"update stock").show();

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }

    @FXML
    void txtsearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        var model = new StockModel();
        try {
            StockDto dto = model.searchStock(id);
            if (dto != null){
                fillFielde(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"stock not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void fillFielde(StockDto dto) {
        txtId.setText(dto.getP_id());
        txtname.setText(dto.getP_name());
        txtweight.setText(dto.getP_weight());
        txtqty.setText(dto.getP_qty());

    }



}
