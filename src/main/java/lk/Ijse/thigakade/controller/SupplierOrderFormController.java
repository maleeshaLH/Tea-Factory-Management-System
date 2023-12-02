package lk.Ijse.thigakade.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.Ijse.thigakade.dto.RawStockDto;
import lk.Ijse.thigakade.dto.SupplierDto;
import lk.Ijse.thigakade.dto.tm.SupplierOrderTm;
import lk.Ijse.thigakade.model.RawStockModel;
import lk.Ijse.thigakade.model.SupplierModel;
import lk.Ijse.thigakade.model.SupplierOrderModel;
import lk.Ijse.thigakade.model.SupplierPlaceOrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SupplierOrderFormController {

    @FXML
    private Pane btnAddToOrder;

    @FXML
    private ComboBox<String > cmbRawStockId;

    @FXML
    private ComboBox<String > cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colRawStockId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Label lblSupplierOrderId;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblunicPrice;

    @FXML
    private Label lblweight;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierOrderTm> tblSupplierOrder;

    @FXML
    private TextField txtTotalQty;
    private final SupplierPlaceOrderModel supplierPlaceOrderModel = new SupplierPlaceOrderModel();
    private final ObservableList<SupplierOrderTm> obList = FXCollections.observableArrayList();
    private final SupplierModel supplierModel = new SupplierModel();
    private final RawStockModel rawStockModel = new RawStockModel();
    private final SupplierOrderModel supplierOrderModel = new SupplierOrderModel();


    public void initialize() {
        loadSupplierIds();
        loadRawStockIds();
        generateNextSupplierOrderId();
        setDate();
        setTime();
    }

    private void setTime() {
        String date = String.valueOf(LocalDate.now());
        lblTime.setText(date);
    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblDate.setText(date);
    }

    private void generateNextSupplierOrderId() {
        try {
            String orderId = supplierOrderModel.generateNextOrderId();
            lblSupplierOrderId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRawStockIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<RawStockDto> supList = rawStockModel.loadAllRawStock();

            for (RawStockDto dto : supList) {
                obList.add(dto.getDescription());
            }
            cmbRawStockId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> supList = supplierModel.loadAllSupplier();

            for (SupplierDto dto : supList) {
                obList.add(dto.getS_id());
            }
            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnSaveOnAction(ActionEvent event) {
      /*  String id = txtSupllierOrderId.getText();
        String supId = cmbSupplierId.getValue();
        String description = txtDescription.getText();
        int unitPrice = Integer.parseInt(txtunicPrice.getText());
        int weight = Integer.parseInt(txtweight.getText());
        int qty = Integer.parseInt(txtQty.getText());

        List<SupplierOrderTm> tmList = new ArrayList<>();

        for (SupplierOrderTm supplierOrderTm : obList) {
            tmList.add(supplierOrderTm);
        }

        var supplierOrderDto = new SupplierOrderDto(id, supId, description, unitPrice, weight, qty, tmList);

        try {
            boolean isSuccess = supplierPlaceOrderModel.supplierOrder(supplierOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/
    }


    @FXML
    void btnbackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = (FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("supplier Form");
        stage.centerOnScreen();
    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event) throws SQLException {
        String id = cmbSupplierId.getValue();
        SupplierDto dto = SupplierModel.searchSupplier(id);

        lblSupplierName.setText(dto.getS_name());

    }

    @FXML
    public void cmbRawStockOnsction(ActionEvent event) {
        String rs_id = cmbRawStockId.getValue();

        txtTotalQty.requestFocus();

        try {
            RawStockDto dto = rawStockModel.searchRawStocks(rs_id);

            lblDescription.setText(dto.getRs_id());
            lblunicPrice.setText(String.valueOf(dto.getUnit_price()));
            lblweight.setText(String.valueOf(dto.getWeight()));
            lblQty.setText(String.valueOf(dto.getQuality()));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnAddToOrderOnAction(MouseEvent mouseEvent) {
       /*String description = cmbRawStockId.getValue();
        String rs_id = lblDescription.getText();
        int unitPrice = Integer.parseInt(lblunicPrice.getText());
        int qty = Integer.parseInt(txtTotalQty.getText());
        int total = qty * unitPrice;
        Button btn = new Button("removes");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblSupplierOrder.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblSupplierOrder.refresh();

                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblSupplierOrder.getItems().size(); i++) {
            if (rs_id.equals(colRawStockId.getCellData(i))) {
                qty += (int) colqty.getCellData(i);
                total = qty * unitPrice;

                obList.get(i).setQty(qty);
                obList.get(i).setTotal(total);

                tblSupplierOrder.refresh();
                calculateNetTotal();
                return;
            }
        }

        obList.add(new SupplierOrderTm(
                rs_id,
                description,
                qty,
                unitPrice,
                total,
                btn
        ));

        tblSupplierOrder.setItems(obList);
        calculateNetTotal();
        txtTotalQty.clear();
    }*/


        String rs_id = cmbRawStockId.getValue();
        String s_id = cmbSupplierId.getValue();
        int qty = Integer.parseInt(txtTotalQty.getText());
        int unitPrice = Integer.parseInt(lblunicPrice.getText());
        int total = qty * unitPrice;
        Button btn = new Button("remove");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblSupplierOrder.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblSupplierOrder.refresh();

                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblSupplierOrder.getItems().size(); i++) {
            if (rs_id.equals(colRawStockId.getCellData(i))) {
                qty += (int) colqty.getCellData(i);
                total = qty * unitPrice;

                obList.get(i).setQty(qty);
                obList.get(i).setTotal(total);

                tblSupplierOrder.refresh();
                calculateNetTotal();
                return;
            }
        }

        obList.add(new SupplierOrderTm(
                rs_id,
                s_id,
                unitPrice,
                qty,
                total,
                btn
        ));

        tblSupplierOrder.setItems(obList);
        calculateNetTotal();
        txtTotalQty.clear();
    }


    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblSupplierOrder.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        lblTotal.setText(String.valueOf(total));

    }
    @FXML
    public void txtQtyOnAction(MouseEvent mouseEvent) {
        btnAddToOrderOnAction(mouseEvent);
    }
}