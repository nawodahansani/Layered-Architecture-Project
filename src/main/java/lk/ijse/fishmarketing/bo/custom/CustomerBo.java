package lk.ijse.fishmarketing.bo.custom;

import lk.ijse.fishmarketing.bo.SuperBo;
import lk.ijse.fishmarketing.entity.Customer;
import lk.ijse.fishmarketing.model.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBo {
    List<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    CustomerDTO searchByCustomerId(String id) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    int getCustomerCount() throws SQLException, ClassNotFoundException;
}
