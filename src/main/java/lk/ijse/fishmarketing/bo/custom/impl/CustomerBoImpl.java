package lk.ijse.fishmarketing.bo.custom.impl;

import lk.ijse.fishmarketing.bo.custom.CustomerBo;
import lk.ijse.fishmarketing.dao.DAOFactory;
import lk.ijse.fishmarketing.dao.custom.CustomerDAO;
import lk.ijse.fishmarketing.entity.Customer;
import lk.ijse.fishmarketing.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.Customer);

    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        List<Customer> customerList = customerDAO.getAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customerList){
            CustomerDTO customerDTO = new CustomerDTO(customer.getId(),customer.getName(), customer.getAddress(), customer.getTel());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(), customerDTO.getTel()));
    }
    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException{
        return customerDAO.update(new Customer(customerDTO.getId(),customerDTO.getName(), customerDTO.getAddress(), customerDTO.getTel()));
    }
    @Override
    public CustomerDTO searchByCustomerId(String id) throws SQLException, ClassNotFoundException{
        Customer customer = customerDAO.searchById(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getTel());
    }
    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException{
        return customerDAO.delete(id);
    }
    @Override
    public int getCustomerCount() throws SQLException, ClassNotFoundException {
        return customerDAO.getCount();
    }
}
