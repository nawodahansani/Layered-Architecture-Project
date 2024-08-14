package lk.ijse.fishmarketing.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.fishmarketing.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane rootNode;

    private void checkCredential(String userName, String password) throws SQLException, IOException {
        String sql = "SELECT password FROM user WHERE name = ?";
                 //get uId & password from the Database

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setObject(1,userName);

        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            String p = resultSet.getString("password");

            if(password.equals(p)) {          //check whether password & uId are correct or incorrect
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
                clearFields();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user name can't be found!").show();
            clearFields();
        }
    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            checkCredential(userName, password);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void  signUpOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Register Form");

        stage.show();
    }

    private void clearFields() {
        txtUserName.setText("");
        txtPassword.setText("");

    }
}
