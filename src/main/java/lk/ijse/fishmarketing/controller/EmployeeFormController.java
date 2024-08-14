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
import lk.ijse.fishmarketing.bo.custom.EmployeeBo;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.Employee;
import lk.ijse.fishmarketing.model.EmployeeDTO;
import lk.ijse.fishmarketing.tm.EmployeeTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeFormController {
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colTel;
    @FXML
    private TableView<EmployeeTm> tblEmployee;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtTel;

    EmployeeBo employeeBo = (EmployeeBo) BoFactory.getBoFactory().getBo(BoFactory.Type.Employee);

    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllEmployees() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDTO> employeeList = employeeBo.getAllEmployee();
            for (EmployeeDTO employee : employeeList) {
                EmployeeTm tm = new EmployeeTm(
                        employee.getId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getTel()
                );

                obList.add(tm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void saveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        if (isValied()) {
            try {
                boolean isSaved = employeeBo.saveEmployee(new EmployeeDTO(id, name, address, tel));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "employee is saved!").show();
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
            boolean isUpdated = employeeBo.updateEmployee(new EmployeeDTO(id, name, address, tel));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee is updated!").show();
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

        Employee employee = employeeBo.searchByEmployeeId(id);
        if (employee != null) {
            txtId.setText(employee.getId());
            txtName.setText(employee.getName());
            txtAddress.setText(employee.getAddress());
            txtTel.setText(employee.getTel());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "employee is not found!").show();
            clearFields();
        }
    }


    @FXML
    void deleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = employeeBo.deleteEmployee(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee is deleted!").show();
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

    public void txtEmployeeIDKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.ID,txtId);
    }

    public void txtEmployeeNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.NAME,txtName);
    }

    public void txtEmployeeAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.fishmarketing.Utill.TextField.ADDRESS,txtAddress);
    }

    public void txtEmployeeTelOnKeyReleased(KeyEvent keyEvent) {
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
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/EmployeeBill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("Employee Id",txtId.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

}
