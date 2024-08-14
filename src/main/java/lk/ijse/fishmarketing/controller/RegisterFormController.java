package lk.ijse.fishmarketing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.fishmarketing.bo.BoFactory;
import lk.ijse.fishmarketing.bo.custom.UserBo;
import lk.ijse.fishmarketing.db.DbConnection;
import lk.ijse.fishmarketing.entity.User;
import lk.ijse.fishmarketing.model.UserDTO;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {
   @FXML
   private TextField txtUserId;
   @FXML
   private TextField txtName;
   @FXML
   private TextField txtPw;

   UserBo userBo = (UserBo) BoFactory.getBoFactory().getBo(BoFactory.Type.User);
    /*private boolean userSaving(String userId,String name,String password) throws SQLException {
        String sql = "INSERT INTO user VALUES(?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1,userId);
        statement.setObject(2,name);
        statement.setObject(3,password);

        return statement.executeUpdate() > 0;
    }*/

    @FXML
    void registerOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String password = txtPw.getText();

        try {
            boolean issaved = userBo.saveUser(new User(userId,name,password));
            //boolean issaved = userSaving(userId,name,password);
            if (issaved){
               new Alert (Alert.AlertType.CONFIRMATION,"user is saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
