package kp.control.UserControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.model.*;
import kp.util.HashPassword;
import kp.util.WarningBox;

public class RegisterAdminController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<String> roleChoice;

    private Client client;
    private Stage stage;

    public void setClient(Client client)
    {
        this.client = client;
    }
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void initialize()
    {
        roleChoice.getItems().addAll("администратор", "старший администратор");
    }

    @FXML
    public void handleSendButton(ActionEvent event)
    {
        if(loginField.getText().isEmpty() || passwordField.getText().isEmpty() || roleChoice.getValue().isEmpty())
        {
            WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
            return;
        }

        String choice = "REGISTER_ADMIN";
        String login = loginField.getText();
        String password = passwordField.getText();
        String role = roleChoice.getValue();

        User user = new User();
        user.setLogin(login);
        byte[] hash_pass = HashPassword.getHash(password);
        user.setPassword(hash_pass);
        if(role.equals("администратор"))
        {
            user.setRole(2);
        }
        else if(role.equals("старший администратор")){
            user.setRole(1);
        }
        user.setIsBlocked(false);

        client.sendObjToServer(choice);
        client.sendObjToServer(user);

        String response = null;

        try
        {
            response = (String) client.getIn().readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            client.closeAllStreams();
        }

        if(response.equals("ERROR"))
        {
            WarningBox.showWarningBox("Пользователь с таким логином уже существует!");
        }
        else if(response.equals("OK"))
        {
            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_hadmin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);

                MainHadminController mhaController = fxmlLoader.getController();
                mhaController.setClient(client);
                mhaController.setStage(stage);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
