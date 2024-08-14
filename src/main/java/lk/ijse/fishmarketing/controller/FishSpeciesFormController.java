package lk.ijse.fishmarketing.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.fishmarketing.Utill.Regex;
import lk.ijse.fishmarketing.bo.BoFactory;
import lk.ijse.fishmarketing.bo.custom.FishSpeciesBo;
import lk.ijse.fishmarketing.model.FishSpeciesDTO;
import lk.ijse.fishmarketing.tm.FishSpeciesTm;

import java.sql.SQLException;
import java.util.List;

public class FishSpeciesFormController {

    public AnchorPane rootNode;
    public TextField txtQty;
    public TextField txtName;
    public TextField txtId;
    public TextField txtPrice;
    public TableView<FishSpeciesTm> tblFishSpecies;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colName;
    public TableColumn<?, ?> colPrice;
    public TableColumn<?, ?> colQty;
    public JFXComboBox<String> cmbEId;
    public TableColumn<?, ?> coleId;
    FishSpeciesBo fishSpeciesBo = (FishSpeciesBo) BoFactory.getBoFactory().getBo(BoFactory.Type.FishSpecies);

    public void initialize() {
        setCellValueFactory();
        loadAllSpiecies();
        getEmployeeId();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("marketPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        coleId.setCellValueFactory(new PropertyValueFactory<>("eId"));
    }

    private void loadAllSpiecies() {
        ObservableList<FishSpeciesTm> obList = FXCollections.observableArrayList();

        try {
            List<FishSpeciesDTO> fishSpeciesList = fishSpeciesBo.getAllSpecies();
            for (FishSpeciesDTO fishSpecies : fishSpeciesList) {
                FishSpeciesTm tm = new FishSpeciesTm(
                        fishSpecies.getId(),
                        fishSpecies.getName(),
                        fishSpecies.getMarketPrice(),
                        fishSpecies.getQty(),
                        fishSpecies.getEId()
                );

                obList.add(tm);
            }

            tblFishSpecies.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void deleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            boolean isDeleted = fishSpeciesBo.deleteSpecies(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "fish species is deleted!").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        String eId = cmbEId.getValue();

        try {
            boolean isSaved = fishSpeciesBo.saveSpecies(new FishSpeciesDTO(id, name, qty , price, eId));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "fish species is saved!").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtQty.setText("");
        txtPrice.setText("");
        cmbEId.setValue("");
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        String eId = cmbEId.getValue();   //

        try {
            boolean isUpdated = fishSpeciesBo.updateSpecies(new FishSpeciesDTO(id, name, qty,price ,eId));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "fish species is updated!").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();

        FishSpeciesDTO fishSpecies = fishSpeciesBo.searchBySpeciesId(id);
        if (fishSpecies != null) {
            txtId.setText(fishSpecies.getId());
            txtName.setText(fishSpecies.getName());
            txtPrice.setText(String.valueOf(fishSpecies.getMarketPrice()));
            cmbEId.setValue(fishSpecies.getEId());  //
            txtQty.setText(String.valueOf(fishSpecies.getQty()));

        } else {
            new Alert(Alert.AlertType.INFORMATION, "fish species is not found!").show();
            clearFields();
        }
    }

    private void getEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = fishSpeciesBo.getEmployeeIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbEId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtFishIDKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.ID,txtId);
    }

    public void txtFishNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.NAME,txtName);
    }

    public void txtFishPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.PRICE,txtPrice);
    }

    public void txtFishQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.QTY,txtQty);
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.ID,txtId)) return false;
        if (!Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.NAME,txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.PRICE,txtPrice)) return false;
        if (!Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.QTY,txtQty)) return false;
        return true;
    }
}
