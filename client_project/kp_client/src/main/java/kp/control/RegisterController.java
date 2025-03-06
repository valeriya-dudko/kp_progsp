package kp.control;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import kp.Client;
import kp.MainMenuClient;
import kp.util.*;
import kp.model.*;

public class RegisterController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<Company> compChoice;
    @FXML
    private TextField nameField;

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
        compChoice.getItems().addAll(LoginController.allComp);
    }

    @FXML
    public void handleSendButton(ActionEvent event) {
        if(loginField.getText().equals("") || passwordField.getText().equals("") || nameField.getText().equals("")
                || compChoice.getSelectionModel().getSelectedItem() == null)
        {
            WarningBox.showWarningBox("Заполните все поля!");
            return;
        }

        String choice = "REGISTER";
        String login = loginField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        Company company = compChoice.getValue();

        User user = new User();
        user.setLogin(login);
        byte[] hash_pass = HashPassword.getHash(password);
        user.setPassword(hash_pass);
        user.setRole(3);

        Declarant declarant = new Declarant();
        declarant.setCompany(company);
        declarant.setUser(user);
        declarant.setName(name);

        //loginField.clear();
        //passwordField.clear();


        client.sendObjToServer(choice);
        //client.sendObjToServer(user);
        client.sendObjToServer(declarant);

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
//                MainMenuClient menu = new MainMenuClient();
//                menu.switchScenes("/login.fxml", stage);
//                RegisterController controller = new RegisterController();

                FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/login.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);

                LoginController loginController = fxmlLoader.getController();
                loginController.setClient(client);
                loginController.setStage(stage);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    @FXML
    public void handleToLogin(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        LoginController loginController = fxmlLoader.getController();
        loginController.setClient(client);
        loginController.setStage(stage);
    }
}
