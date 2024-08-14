package lk.ijse.fishmarketing.dao.custom;

import lk.ijse.fishmarketing.dao.CrudDAO;
import lk.ijse.fishmarketing.entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order> {
    public String getCurrentId() throws SQLException, ClassNotFoundException;
}
