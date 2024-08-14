package lk.ijse.fishmarketing.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fishmarketing.bo.BoFactory;
import lk.ijse.fishmarketing.bo.custom.CustomerBo;
import lk.ijse.fishmarketing.bo.custom.EmployeeBo;
import lk.ijse.fishmarketing.bo.custom.FishSpeciesBo;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.model.CustomerDTO;
import lk.ijse.fishmarketing.model.FishSpeciesDTO;
import lk.ijse.fishmarketing.tm.CustomerTm;
import lk.ijse.fishmarketing.tm.FishSpeciesTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DashboardFormController {
    public AnchorPane rootNode;
    public Label customerCount;
    public Label lblUser;
    public AnchorPane node;
    public TableView<FishSpeciesTm> tblFish;
    public TableColumn<?, ?> colName;
    public TableColumn<?, ?> colPrice;
    public TableColumn<?, ?> colQty;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colCname;
    public TableColumn<?, ?> colAddress;
    public TableColumn<?, ?> colTel;
    private int count;
    public Label employeeCount;
    private int ecount;
    public Label fishSpeciesCount;
    private int fcount;
    public Label tankCount;
    private int tcount;
    @FXML
    private Label date;
    FishSpeciesBo fishSpeciesBo = (FishSpeciesBo) BoFactory.getBoFactory().getBo(BoFactory.Type.FishSpecies);
    CustomerBo customerBo = (CustomerBo) BoFactory.getBoFactory().getBo(BoFactory.Type.Customer);
    EmployeeBo employeeBo = (EmployeeBo) BoFactory.getBoFactory().getBo(BoFactory.Type.Employee);


    public void initialize() {
        try {
            count = customerBo.getCustomerCount();
            ecount = employeeBo.getEmployeeCount();
            fcount = fishSpeciesBo.getSpeciesCount();
            //ecount = getEmployeeCount();
            //fcount = getFishSpeciesCount();
            //tcount = getTankCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setCustomerCount(count);
        setEmployeeCount(ecount);
        setFishSpeciesCount(fcount);
       // setTankCount(tcount);
        setDate();
        setCellValueFactory();
        loadAllSpecies();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("marketPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllSpecies() {
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

            tblFish.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {

            List<CustomerDTO> customerList = customerBo.getAllCustomer();
            for (CustomerDTO customer : customerList) {
                CustomerTm tm = new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getTel()
                );

                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCustomerCount(int count) {
        customerCount.setText(String.valueOf(count));
    }

   /* private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("customer_count");
        }
        return 0;
    }*/

    private void setEmployeeCount(int ecount) {
        employeeCount.setText(String.valueOf(ecount));
    }

   /* private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("employee_count");
        }
        return 0;
    }*/

    private void setFishSpeciesCount(int fcount) {
        fishSpeciesCount.setText(String.valueOf(fcount));
    }

   /* private int getFishSpeciesCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS fishspecies_count FROM fishspecies";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("fishspecies_count");
        }
        return 0;
    }*/

   /* private void setTankCount(int tcount) {
        tankCount.setText(String.valueOf(tcount));
    }

    private int getTankCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS tank_count FROM tank";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("tank_count");
        }
        return 0;
    }

    */

   @FXML
   void employeeOnAction(ActionEvent actionEvent) throws IOException {
       try {
           rootNode.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml")));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
    }

    @FXML
    void customerOnAction(ActionEvent actionEvent) {
        try {
            rootNode.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void fishSpeciesOnAction(ActionEvent actionEvent) throws IOException {
        try {
            rootNode.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/fish_species_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        try {
            rootNode.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/orders_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void paymentOnAction(ActionEvent actionEvent) throws IOException {
        try {
            rootNode.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
   /* void tankOnAction(ActionEvent actionEvent) throws IOException {
        try {
            rootNode.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/tank_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    */

    public void settingOnAction(ActionEvent actionEvent) throws IOException {
        try {
            rootNode.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/setting_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void homeOnAction(ActionEvent actionEvent) throws IOException {
        try {
            node.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        date.setText(String.valueOf(now));
    }

}


