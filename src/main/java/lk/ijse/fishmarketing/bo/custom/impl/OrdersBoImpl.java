package lk.ijse.fishmarketing.bo.custom.impl;

import lk.ijse.fishmarketing.bo.custom.OrdersBo;
import lk.ijse.fishmarketing.dao.DAOFactory;
import lk.ijse.fishmarketing.dao.custom.CustomerDAO;
import lk.ijse.fishmarketing.dao.custom.FishSpeciesDAO;
import lk.ijse.fishmarketing.dao.custom.OrderDAO;
import lk.ijse.fishmarketing.dao.custom.OrderDetailDAO;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.*;
import lk.ijse.fishmarketing.model.CustomerDTO;
import lk.ijse.fishmarketing.model.FishSpeciesDTO;
import lk.ijse.fishmarketing.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersBoImpl implements OrdersBo {
    FishSpeciesDAO fishSpeciesDAO = (FishSpeciesDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.FishSpecies);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.Customer);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.Order);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.OrderDetail);

    @Override
    public List<String> getSpeciesIds() throws SQLException, ClassNotFoundException {
        return fishSpeciesDAO.getIds();
    }
    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getIds();
    }
    @Override
    public String getCurrentOrderId() throws SQLException, ClassNotFoundException{
        return orderDAO.getCurrentId();
    }
    @Override
    public CustomerDTO searchByCustomerId(String id) throws SQLException, ClassNotFoundException{
        Customer customer = customerDAO.searchById(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getTel());
    }
    @Override
    public FishSpeciesDTO searchBySpeciesId(String id) throws SQLException, ClassNotFoundException {
        FishSpecies fishSpecies = fishSpeciesDAO.searchById(id);
        return new FishSpeciesDTO(fishSpecies.getId(), fishSpecies.getName(), fishSpecies.getQty(),fishSpecies.getMarketPrice(), fishSpecies.getEId());
    }

    @Override
    public boolean placeOrder(String orderId , Date date,String customerId ,List<OrderDetailDTO> orderDetails) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = orderDAO.save(new Order(orderId,date,customerId));
           // boolean isOrderSaved = OrderRepo.save(po.getOrder());
            if (isOrderSaved) {
                boolean isQtyUpdated = updates(new ArrayList<>(orderDetails));
                //boolean isQtyUpdated = OrderDetailRepo.updates(po.getOdList());
                if (isQtyUpdated) {
                    boolean isOrderDetailSaved = saves(new ArrayList<>(orderDetails));
                    //boolean isOrderDetailSaved = OrderDetailRepo.saves(po.getOdList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updates(List<OrderDetailDTO> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetailDTO od : odList) {
            boolean isUpdateQty = fishSpeciesDAO.updateQty(od.getFishId(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saves(List<OrderDetailDTO> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetailDTO od : odList) {
            boolean isSaved = orderDetailDAO.save(new OrderDetail(od.getOrderId(),od.getFishId(),od.getQty(),od.getUnitPrice()));
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }


}
