package lk.ijse.fishmarketing.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.fishmarketing.Utill.Regex;
import lk.ijse.fishmarketing.bo.BoFactory;
import lk.ijse.fishmarketing.bo.custom.CustomerBo;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.model.CustomerDTO;
import lk.ijse.fishmarketing.tm.CustomerTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerFormController {
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colTel;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TableView<CustomerTm> tblCustomer;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtTel;
    CustomerBo customerBo = (CustomerBo) BoFactory.getBoFactory().getBo(BoFactory.Type.Customer);

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDTO> customerList = customerBo.getAllCustomer();

            for (CustomerDTO customer : customerList) {
               obList.add(new CustomerTm(customer.getId(),
                       customer.getName(),
                       customer.getAddress(),
                       customer.getTel())
               );
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        if (isValied()) {
            try {
                boolean isSaved = customerBo.saveCustomer(new CustomerDTO(id,name,address,tel));
                //boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer is saved!").show();
                    initialize();
                    clearFields();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        try {
            boolean isUpdated = customerBo.updateCustomer(new CustomerDTO(id, name, address, tel));
            //boolean isUpdated = CustomerRepo.update(customer);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer is updated!").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();


        CustomerDTO customer = customerBo.searchByCustomerId(id);
        if (customer != null) {
            txtId.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtTel.setText(customer.getTel());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer is not found!").show();
            clearFields();
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = customerBo.deleteCustomer(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer is deleted!").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    public void txtCustomerIDKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.ID,txtId);
    }

    public void txtCustomerNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.NAME,txtName);
    }

    public void txtCustomerAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.ADDRESS,txtAddress);
    }

    public void txtCustomerTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.TEL,txtTel);
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.ID,txtId)) return false;
        if (!Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.NAME,txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.ADDRESS,txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.TEL,txtTel)) return false;
        return true;
    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/CustomerBill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("Customer Id",txtId.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
