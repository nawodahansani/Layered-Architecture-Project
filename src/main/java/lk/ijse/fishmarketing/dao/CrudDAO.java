package lk.ijse.fishmarketing.dao;

import lk.ijse.fishmarketing.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO{
    public  List<T> getAll() throws SQLException, ClassNotFoundException;
    public List<String> getIds() throws SQLException, ClassNotFoundException;
    public boolean save(T DTO) throws SQLException, ClassNotFoundException;
    public boolean update(T DTO) throws SQLException, ClassNotFoundException;
    public T searchById(String id) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public int getCount() throws SQLException, ClassNotFoundException ;
}
