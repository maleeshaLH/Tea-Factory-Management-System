package lk.Ijse.thigakade.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.Ijse.thigakade.dto.PreparedstockDto;
import lk.Ijse.thigakade.dto.RawStockDto;
import lk.Ijse.thigakade.dto.StockOrderDto;
import lk.Ijse.thigakade.dto.tm.StockOrderTm;
import lk.Ijse.thigakade.model.RawStockModel;
import lk.Ijse.thigakade.model.StockModel;
import lk.Ijse.thigakade.model.StockPlaceOrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockDetailsFormcontroller {
    @FXML
    public AnchorPane root;
    @FXML
    public JFXComboBox <String >cmbRawStockId;
    @FXML
    public Label lblPreperedDescription;
    @FXML
    public TableView<StockOrderTm> tblStockOrder;
    @FXML
    private Label lblDescription;
    @FXML
    private JFXButton btnAddtoOrder;

    @FXML
    private JFXComboBox<String > cmbPreparedStockName;

    @FXML
    private JFXComboBox<String > cmbRawStockName;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPreparedStockId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblPreparedQty;

    @FXML
    private Label lblPreparedUnitPrice;

    @FXML
    private Label lblPreparedWeight;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblStockDetailsDate;

    @FXML
    private Label lblStockDetailsId;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblWeight;

    @FXML
    private TextField txtQty;
    private final RawStockModel rawStockModel = new RawStockModel();
    private final StockModel stockModel = new StockModel();
    private final ObservableList<StockOrderTm> obList = FXCollections.observableArrayList();
    private  final StockPlaceOrderModel stockPlaceOrderModel = new StockPlaceOrderModel();

    public void initialize() {
        setCellValueFactory();
        setDate();
        loadRawStockIds();
        loadPreparedStockIds();
    }

    private void setCellValueFactory() {
        colPreparedStockId.setCellValueFactory(new PropertyValueFactory<>("PreparedStockId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void loadPreparedStockIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<PreparedstockDto> stockList = stockModel.loadAllStockIds();

            for (PreparedstockDto stockDto : stockList) {
                obList.add(stockDto.getP_id());
            }

            cmbPreparedStockName.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRawStockIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<RawStockDto> stockList = rawStockModel.loadAllStockNames();

            for (RawStockDto stockDto : stockList) {
                obList.add(stockDto.getRs_id());
            }

            cmbRawStockId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblStockDetailsDate.setText(date);
    }


    @FXML
    void btnAddtoOrderOnAction(ActionEvent event) {
        String p_id = cmbPreparedStockName.getValue();
        String rs_id = cmbRawStockId.getValue();
        String description = lblPreperedDescription.getText();
        int unitPrice = Integer.parseInt(lblPreparedUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        int total = qty * unitPrice;
        Button btn = new Button("removes");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblStockOrder.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblStockOrder.refresh();

                calculateTotalPrice();
            }
        });

        for (int i = 0; i < tblStockOrder.getItems().size(); i++) {
            if (p_id.equals(colPreparedStockId.getCellData(i))) {
                qty += (int) colQty.getCellData(i);
                total = qty * unitPrice;

                obList.get(i).setQty(qty);
                obList.get(i).setTotal(total);

                tblStockOrder.refresh();
                calculateTotalPrice();
                return;
            }
        }

        obList.add(new StockOrderTm(
                p_id,
                rs_id,
                description,
                unitPrice,
                qty,
                total,
                btn
        ));

        tblStockOrder.setItems(obList);
        calculateTotalPrice();
        txtQty.clear();
    }

    private void calculateTotalPrice() {
        int total = 0;
        for (int i = 0; i < tblStockOrder.getItems().size(); i++) {
            total += (int) colTotalPrice.getCellData(i);
        }

        lblTotalPrice.setText(String.valueOf(total));
    }

    @FXML
    void btnConformOnAction(ActionEvent event) {
        String RawStockId = cmbRawStockId.getValue();
        String PreparedStockId = cmbPreparedStockName.getValue();
       String Description = lblDescription.getText();
       int unitPrice = Integer.parseInt(lblPreparedUnitPrice.getText());
       int weight = Integer.parseInt(lblWeight.getText());
       int qty = Integer.parseInt(lblQtyOnHand.getText());

        List<StockOrderTm> tmList = new ArrayList<>();

        for (StockOrderTm stockOrderTm : obList) {
            tmList.add(stockOrderTm);
        }

        var stockOrderDto = new StockOrderDto(
                PreparedStockId,
                RawStockId,
                Description,
                unitPrice,
                weight,
                qty,
                tmList
        );

        try {
            System.out.println("1");
            boolean isSuccess = stockPlaceOrderModel.stockOrder(stockOrderDto);
            if(isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnbackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/dashBoard_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("DashBoard Form");
        stage.centerOnScreen();

    }

    @FXML
    void cmbPreparedStockOnAction(ActionEvent event) {
        String id = cmbPreparedStockName.getValue();

        txtQty.requestFocus();

        try {
            PreparedstockDto dto = stockModel.searchStock(id);

            lblPreperedDescription.setText(dto.getDescription());
            lblPreparedUnitPrice.setText(String.valueOf(dto.getUnit_price()));
            lblPreparedWeight.setText(String.valueOf(dto.getWeight()));
            lblPreparedQty.setText(String.valueOf(dto.getQty()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbRawStockOnAction(ActionEvent event) {
        String id = (String) cmbRawStockId.getValue();

        txtQty.requestFocus();

        try {
            RawStockDto dto = rawStockModel.searchRawStock(id);


            lblDescription.setText(dto.getDescription());
            lblUnitPrice.setText(String.valueOf(dto.getUnit_price()));
            lblWeight.setText(String.valueOf(dto.getWeight()));
            lblQtyOnHand.setText(String.valueOf(dto.getQuality()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
    btnAddtoOrderOnAction(event);
    }

}
