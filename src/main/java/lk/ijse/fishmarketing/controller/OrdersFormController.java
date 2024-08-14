package lk.ijse.fishmarketing.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.fishmarketing.bo.BoFactory;
import lk.ijse.fishmarketing.bo.custom.OrdersBo;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.model.*;
import lk.ijse.fishmarketing.tm.CartTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrdersFormController {

    public Label lblDate;
    public Label lblOrderId;
    public JFXComboBox<String> customerId;
    public Label lblCustomerName;
    public JFXComboBox<String> fishId;
    public Label lblFishName;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public TextField txtQty;
    public Button btnAddtoCart;
    public TableView<CartTm> tblOrderCart;
    public TableColumn<?, ?> colFishCode;
    public TableColumn<?, ?> colFishName;
    public TableColumn<?, ?> colQty;
    public TableColumn<?, ?> colUnitPrice;
    public TableColumn<?, ?> colTotal;
    public TableColumn<?, ?> colAction;
    public Label lblNetTotal;
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();
    OrdersBo ordersBo = (OrdersBo) BoFactory.getBoFactory().getBo(BoFactory.Type.Orders);


    public void initialize(){
        setDate();
        getCurrentOrderId();
        getCustomerId();
        getFishId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colFishCode.setCellValueFactory(new PropertyValueFactory<>("fishCode"));
        colFishName.setCellValueFactory(new PropertyValueFactory<>("fishName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getFishId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = ordersBo.getSpeciesIds();

            for (String code : codeList) {
                obList.add(code);
            }
            fishId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = ordersBo.getCustomerIds();

            for(String id : idList) {
                obList.add(id);
            }

            customerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentOrderId() {
        try {
            String currentId = ordersBo.getCurrentOrderId();

            String nextOrderId = generateNextOrderId(currentId);
            System.out.println(nextOrderId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    public void customerOnAction(ActionEvent actionEvent) {
        String id = customerId.getValue();
        try {
            CustomerDTO customer = ordersBo.searchByCustomerId(id);

            lblCustomerName.setText(customer.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void fishOnAction(ActionEvent actionEvent) {
        String code = fishId.getValue();

        try {
            FishSpeciesDTO species = ordersBo.searchBySpeciesId(code);
            if(species != null) {
                lblFishName.setText(species.getName());
                lblUnitPrice.setText(String.valueOf(species.getMarketPrice()));
                lblQtyOnHand.setText(String.valueOf(species.getQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void QtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String fishCode = fishId.getValue();
        String fishName = lblFishName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if(fishCode.equals(colFishCode.getCellData(i))) {

                CartTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblOrderCart.refresh();

                calculateNetTotal();
                return;
            }
        }

        obList.add(new CartTm(fishCode, fishName, qty, unitPrice, total, btnRemove));

        tblOrderCart.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");

    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String cusId = customerId.getValue();
        Date date = Date.valueOf(LocalDate.now());

        var order = new OrderDTO(orderId, cusId, date);

        List<OrderDetailDTO> odList = new ArrayList<>();

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            OrderDetailDTO od = new OrderDetailDTO(
                    orderId,
                    tm.getFishCode(),
                    tm.getQty(),
                    tm.getUnitPrice()
            );

            odList.add(od);
        }

        PlaceOrderDTO po = new PlaceOrderDTO(order, odList);
        try {
            boolean isPlaced = ordersBo.placeOrder(orderId,date,cusId,odList);
            //boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order is Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed is Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/OrderBill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("oId",ordersBo.getCurrentOrderId());
        //data.put("oId",OrderRepo.getCurrentId());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
