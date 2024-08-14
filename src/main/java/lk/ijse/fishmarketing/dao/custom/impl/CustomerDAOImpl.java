package lk.ijse.fishmarketing.dao.custom.impl;

import lk.ijse.fishmarketing.dao.SQLUtil;
import lk.ijse.fishmarketing.dao.custom.CustomerDAO;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        List<Customer> customerList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");
        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            Customer customer = new Customer(id,name,address,tel);
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        List<String> idList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT cId FROM customer");
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?, ?, ?, ?)",customer.getId(),customer.getName(),customer.getAddress(),customer.getTel());
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET name = ?, address = ?, telNumber = ? WHERE cId = ?",customer.getName(),customer.getAddress(),customer.getTel(),customer.getId());
    }

    @Override
    public Customer searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE cId = ?",id);

        if (resultSet.next()) {
            String customer_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);

            Customer customer = new Customer(customer_id, name, address, tel);
            return customer;
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE cId = ?",id);
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS customer_count FROM customer");

        if (resultSet.next()) {
            return resultSet.getInt("customer_count");
        }
        return 0;
    }
}
