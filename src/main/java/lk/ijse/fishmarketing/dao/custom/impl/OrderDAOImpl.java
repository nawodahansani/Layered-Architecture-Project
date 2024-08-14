package lk.ijse.fishmarketing.dao.custom.impl;

import lk.ijse.fishmarketing.dao.SQLUtil;
import lk.ijse.fishmarketing.dao.custom.OrderDAO;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.Order;
import lk.ijse.fishmarketing.model.OrderDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT oId FROM orders ORDER BY oId DESC LIMIT 1");
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }
    @Override
    public boolean save(Order order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders VALUES(?, ?, ?)",order.getOrderId(),order.getDate(),order.getCustomerId());
    }

    @Override
    public List<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Order DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
