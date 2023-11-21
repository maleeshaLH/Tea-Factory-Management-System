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
import lk.Ijse.thigakade.dto.CustomerDto;
import lk.Ijse.thigakade.dto.PlaceOrderDto;
import lk.Ijse.thigakade.dto.StockDto;
import lk.Ijse.thigakade.dto.tm.CartTm;
import lk.Ijse.thigakade.model.CustomerModel;
import lk.Ijse.thigakade.model.OrderModel;
import lk.Ijse.thigakade.model.PlaceOrderModel;
import lk.Ijse.thigakade.model.StockModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderFormcontroller {
    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final StockModel stockModel = new StockModel();
    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();
    private final PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    @FXML
    private TableView<CartTm> tblOrderCart;
    @FXML
    private Label lblNetTotal;

    @FXML
    private TableColumn<?, ?> cloAction;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colProductionId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colWeight;
    @FXML
    public TextField txtQty;
    @FXML
    public JFXButton btnAddToCart;

    @FXML
    private Label lblOrderid;
    @FXML
    private JFXComboBox<String > cmbStockId;
    @FXML
    private Label lblQty;

    @FXML
    private Label lblStockName;

    @FXML
    private Label lblWeight;


    @FXML
    private Label lblCustomerName;
    @FXML
    private JFXComboBox<String > cmbCustomerId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private AnchorPane root;

    public void initialize() {
        setCellValueFactory();
        loadStockIds();
        loadCustomerIds();
        generateNextOrderId();
        setDate();
    }

    private void setCellValueFactory() {
        colProductionId.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        cloAction.setCellValueFactory(new PropertyValueFactory<>("btn"));


    }


    private void loadStockIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<StockDto> stocklist = stockModel.loadAllStock();

            for (StockDto Dto : stocklist) {
                obList.add(Dto.getP_id());
            }

            cmbStockId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = customerModel.loadAllCustomers();

            for (CustomerDto dto : cusList) {
                obList.add(dto.getId());
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblOrderDate.setText(date);
    }

    private void generateNextOrderId() {
        try {
            String orderId = orderModel.generateNextOrderId();
            lblOrderid.setText(orderId);
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


    public void cbmCustomerOnAction(ActionEvent event) throws SQLException {
        String id = cmbCustomerId.getValue();
        CustomerDto dto = customerModel.searchCustomer(id);

        lblCustomerName.setText(dto.getFirst_name());


    }

    @FXML
    void cbmStockOnAction(ActionEvent event) {
        String id = cmbStockId.getValue();

       // txtQty.requestFocus();

        try {
            StockDto dto = stockModel.searchStock(id);

            lblStockName.setText(dto.getP_name());
            lblWeight.setText(String.valueOf(dto.getP_weight()));
            lblQty.setText(String.valueOf(dto.getP_qty()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderid.getText();
        String cusId = cmbCustomerId.getValue();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());

        List<CartTm> tmList = new ArrayList<>();

        for (CartTm cartTm : obList) {
            tmList.add(cartTm);
        }

        var placeOrderDto = new PlaceOrderDto(
                orderId,
                cusId,
                date,
                tmList
        );

        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if(isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String p_Id = cmbStockId.getValue();
        String name = lblStockName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double weight = Double.parseDouble(lblWeight.getText());
        double total = qty * weight;
        Button btn = new Button("remove");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblOrderCart.refresh();

                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if (p_Id.equals(colProductionId.getCellData(i))) {
                qty += (int) colQty.getCellData(i);
                total = qty * weight;

                obList.get(i).setQty(qty);
                obList.get(i).setTot(total);

                tblOrderCart.refresh();
                calculateNetTotal();
                return;
            }
        }

        obList.add(new CartTm(
                p_Id,
                name,
                (int) weight,
                qty,
                total,
                btn
        ));

        tblOrderCart.setItems(obList);
        calculateNetTotal();
        txtQty.clear();

    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        lblNetTotal.setText(String.valueOf(total));
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);

    }

}
