package lk.ijse.fishmarketing.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.fishmarketing.bo.BoFactory;
import lk.ijse.fishmarketing.bo.custom.UserBo;
import lk.ijse.fishmarketing.model.UserDTO;

import java.sql.SQLException;

public class SettingFormController {
    public TextField txtUserId;
    public TextField txtUserName;
    public TextField txtPassword;
    UserBo userBo = (UserBo) BoFactory.getBoFactory().getBo(BoFactory.Type.User);

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = txtUserId.getText();

        try {
            boolean isDeleted = userBo.deleteUser(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "user is deleted!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateOnAction(ActionEvent actionEvent) {
        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            boolean isUpdated = userBo.updateUser(new UserDTO(id, name, password));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "user is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    private void clearFields() {
        txtUserId.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtUserId.getText();

        UserDTO user = userBo.searchByUserId(id);
        if (user != null) {
            txtUserId.setText(user.getUId());
            txtUserName.setText(user.getName());
            txtPassword.setText(user.getPassword());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "user is not found!").show();
            clearFields();
        }
    }
}
