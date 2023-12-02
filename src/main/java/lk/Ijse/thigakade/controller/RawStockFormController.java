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
import lk.Ijse.thigakade.dto.RawStockDto;
import lk.Ijse.thigakade.dto.tm.RawStockTm;
import lk.Ijse.thigakade.model.RawStockModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RawStockFormController {
    @FXML
    public TextField txtUnicprice;
    @FXML
    public TableColumn <? ,?> colUnic_price;
    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQuality;

    @FXML
    private TableColumn<?, ?> colR_id;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<RawStockTm> tblRawStock;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtR_id;

    @FXML
    private TextField txtqulity;

    @FXML
    private TextField txtweight;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colR_id.setCellValueFactory(new PropertyValueFactory<>("rs_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnic_price.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colQuality.setCellValueFactory(new PropertyValueFactory<>("quality"));

    }

    private void loadAllCustomers() {
        var model = new RawStockModel();

        ObservableList<RawStockTm> obList = FXCollections.observableArrayList();

        try {
            List<RawStockDto> dtoList = model.getAllRawStock();

            for (RawStockDto dto : dtoList) {
                obList.add(
                        new RawStockTm(
                                dto.getRs_id(),
                                dto.getDescription(),
                                dto.getUnit_price(),
                                dto.getWeight(),
                                dto.getQuality()

                                )
                );
            }

            tblRawStock.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
    void btnClearOnAction(ActionEvent event) {
        clesrFields();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtR_id.getText();

        var rawStockModel = new RawStockModel();
        try {
            boolean isDeleted = rawStockModel.deleteRawStock(id);

            if(isDeleted) {
                tblRawStock.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, " deleted raw stock!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String ra_id = txtR_id.getText();
        String name = txtName.getText();
        int unic_price = Integer.parseInt(txtUnicprice.getText());
        int weight =Integer.parseInt(txtweight.getText());
        int quality = Integer.parseInt(txtqulity.getText());


        var dto = new RawStockDto(ra_id,name,unic_price,weight,quality);

        var model = new RawStockModel();
        try {
            boolean isSaved = model.saveRawStock(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "save raw stock!").show();
                clesrFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clesrFields() {
        txtR_id.setText("");
        txtName.setText("");
        txtUnicprice.setText("");
        txtweight.setText("");
        txtqulity.setText("");

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String ra_id = txtR_id.getText();
        String name = txtName.getText();
        Integer unic_price =Integer.parseInt(txtUnicprice.getText());
        Integer weight =Integer.parseInt(txtweight.getText());
        Integer quality = Integer.parseInt(txtqulity.getText());


        var dto = new RawStockDto(ra_id,name,unic_price,weight,quality);

        var model = new RawStockModel();
        try {
            boolean isUpdated = model.updateRawStock(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "updated raw stock!").show();
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
    void btnsearchOnAction(ActionEvent event) {
        String id = txtR_id.getText();

        var model = new RawStockModel();
        try {
            RawStockDto dto = model.searchRawStock(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void fillFields(RawStockDto dto) {
        txtR_id.setText(dto.getRs_id());
        txtName.setText(dto.getDescription());
        txtUnicprice.setText(String.valueOf(dto.getQuality()));
        txtweight.setText(String.valueOf(dto.getWeight()));
        txtqulity.setText(String.valueOf(dto.getQuality()));

    }

}
