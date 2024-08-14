package lk.ijse.fishmarketing.bo.custom;

import lk.ijse.fishmarketing.bo.SuperBo;
import lk.ijse.fishmarketing.model.CustomerDTO;
import lk.ijse.fishmarketing.model.FishSpeciesDTO;
import lk.ijse.fishmarketing.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface OrdersBo extends SuperBo {
    List<String> getSpeciesIds() throws SQLException, ClassNotFoundException;

    List<String> getCustomerIds() throws SQLException, ClassNotFoundException;

    String getCurrentOrderId() throws SQLException, ClassNotFoundException;

    CustomerDTO searchByCustomerId(String id) throws SQLException, ClassNotFoundException;

    FishSpeciesDTO searchBySpeciesId(String id) throws SQLException, ClassNotFoundException;

    boolean placeOrder(String orderId, Date date,String customerId, List<OrderDetailDTO> orderDetails) throws SQLException;

    boolean updates(List<OrderDetailDTO> odList) throws SQLException, ClassNotFoundException;

    boolean saves(List<OrderDetailDTO> odList) throws SQLException, ClassNotFoundException;
}
