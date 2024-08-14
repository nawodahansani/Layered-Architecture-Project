package lk.ijse.fishmarketing.bo.custom;

import lk.ijse.fishmarketing.bo.SuperBo;
import lk.ijse.fishmarketing.entity.User;
import lk.ijse.fishmarketing.model.UserDTO;

import java.sql.SQLException;

public interface UserBo extends SuperBo {
    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;

    UserDTO searchByUserId(String id) throws SQLException, ClassNotFoundException;

    boolean saveUser(User DTO) throws SQLException, ClassNotFoundException;
}
