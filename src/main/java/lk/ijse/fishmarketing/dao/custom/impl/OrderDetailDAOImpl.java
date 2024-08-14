package lk.ijse.fishmarketing.dao.custom.impl;

import lk.ijse.fishmarketing.dao.SQLUtil;
import lk.ijse.fishmarketing.dao.custom.OrderDetailDAO;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.OrderDetail;
import lk.ijse.fishmarketing.model.OrderDetailDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean save(OrderDetail od) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orderdetail VALUES(?, ?, ?, ?)",od.getOrderId(),od.getFishId(),od.getQty(),od.getUnitPrice());
    }

    //transaction walata oni wenawa place order method eke
    /*public boolean saves(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail od : odList) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;

        public boolean updates(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail od : odList) {
            boolean isUpdateQty = FishSpeciesDAOImpl.updateQty(od.getFishId(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }*/

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(OrderDetail DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail searchById(String id) throws SQLException, ClassNotFoundException {
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
