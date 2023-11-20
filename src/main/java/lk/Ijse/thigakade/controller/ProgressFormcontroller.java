package lk.Ijse.thigakade.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.Ijse.thigakade.dto.ProgressDto;
import lk.Ijse.thigakade.dto.tm.ProgressTm;
import lk.Ijse.thigakade.model.ProgressModel;

import java.sql.SQLException;
import java.util.List;

public class ProgressFormcontroller {

    @FXML
    private TableColumn<?, ?> colamount;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> coltime;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ProgressTm> tblProgress;

    @FXML
    private TextField txtamount;

    @FXML
    private TextField txtdate;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txttime;

    public void initialize() {
        setCellValueFactory();
        loadAllpro();
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void loadAllpro() {
        var model = new ProgressModel();

        ObservableList<ProgressTm> obList = FXCollections.observableArrayList();

        try {
            List<ProgressDto> dtoList = model.getAllProgress();

            for(ProgressDto dto : dtoList) {
                obList.add(
                        new ProgressTm(
                                dto.getId(),
                                dto.getDate(),
                                dto.getTime(),
                                dto.getAmount()
                        )
                );
            }

            tblProgress.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnsaveOnAction(ActionEvent event) {
        String id = txtid.getText();
        String date = txtdate.getText();
        String time = txttime.getText();
        String amount = txtamount.getText();

        var dto = new ProgressDto(id, date, time, amount);

        var model = new ProgressModel();
        try {
            boolean isSaved = model.saveProgress(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
               // clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



}
