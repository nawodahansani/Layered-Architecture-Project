package lk.ijse.fishmarketing.dao;

import lk.ijse.fishmarketing.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDao(DaoType type){
        switch (type){
            case Customer:
                return new CustomerDAOImpl();
            case Employee:
                return new EmployeeDAOImpl();
            case FishSpecies:
                return new FishSpeciesDAOImpl();
            case Order:
                return new OrderDAOImpl();
            case OrderDetail:
                return new OrderDetailDAOImpl();
            case User:
                return new UserDAOImpl();
            default:
                return null;
        }
    }

    public enum DaoType{
       Customer,Employee,FishSpecies,Order,OrderDetail,User ;
    }
}
