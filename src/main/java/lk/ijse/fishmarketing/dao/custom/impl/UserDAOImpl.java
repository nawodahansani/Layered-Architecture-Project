package lk.ijse.fishmarketing.dao.custom.impl;

import lk.ijse.fishmarketing.dao.SQLUtil;
import lk.ijse.fishmarketing.dao.custom.UserDAO;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.User;
import lk.ijse.fishmarketing.model.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User searchById(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM user WHERE uId = ?",id);

        if (resultSet.next()) {
            String uId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);

            User user = new User(uId, name, password);
            return user;
        }
        return null;
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET name = ?, password = ? WHERE uId = ?",user.getName(),user.getPassword(),user.getUId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM user WHERE uId = ?",id);
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(User DTO) throws SQLException, ClassNotFoundException {
    return SQLUtil.execute("INSERT INTO user VALUES(?,?,?)",DTO.getUId(),DTO.getName(),DTO.getPassword());
    }



}
