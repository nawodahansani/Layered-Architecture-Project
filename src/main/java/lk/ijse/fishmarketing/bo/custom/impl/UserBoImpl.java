package lk.ijse.fishmarketing.bo.custom.impl;

import lk.ijse.fishmarketing.bo.custom.UserBo;
import lk.ijse.fishmarketing.dao.DAOFactory;
import lk.ijse.fishmarketing.dao.custom.UserDAO;
import lk.ijse.fishmarketing.entity.User;
import lk.ijse.fishmarketing.model.UserDTO;

import java.sql.SQLException;

public class UserBoImpl implements UserBo {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoType.User);
    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }
    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(userDTO.getUId(),userDTO.getName(), userDTO.getPassword()));
    }
    @Override
    public UserDTO searchByUserId(String id) throws SQLException, ClassNotFoundException{
        User user = userDAO.searchById(id);
        return new UserDTO(user.getUId(), user.getName(), user.getPassword());
    }
    @Override
    public boolean saveUser(User DTO) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(DTO.getUId(), DTO.getName(), DTO.getPassword()));
    }
}
