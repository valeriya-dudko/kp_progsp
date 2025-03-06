package kp.control.UserControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.LoginController;
import kp.model.*;

import java.util.ArrayList;
import java.util.List;

public class MainHadminController {
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

    static List<User> users;

    @FXML
    public void handleLogout(ActionEvent event) throws Exception
    {
        String choice = "LOGOUT";
        client.sendObjToServer(choice);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        LoginController loginController = fxmlLoader.getController();
        loginController.setClient(client);
        loginController.setStage(stage);
    }

    @FXML
    public void handleBlockUser(ActionEvent event) throws Exception
    {
        client.sendObjToServer("BLOCK_USER");

        User userItem;

        users = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            userItem = (User) client.getIn().readObject();
            users.add(userItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/block_user.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        BlockUserController buController = fxmlLoader.getController();
        buController.setClient(client);
        buController.setStage(stage);
    }

    @FXML
    public void handleUnblockUser(ActionEvent event) throws Exception
    {
        client.sendObjToServer("UNBLOCK_USER");

        User userItem;

        users = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            userItem = (User) client.getIn().readObject();
            users.add(userItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/unblock_user.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        UnblockUserController ubuController = fxmlLoader.getController();
        ubuController.setClient(client);
        ubuController.setStage(stage);
    }

    @FXML
    public void handleDeleteUser(ActionEvent event) throws Exception
    {
        client.sendObjToServer("DELETE_USER");

        User userItem;

        users = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            userItem = (User) client.getIn().readObject();
            users.add(userItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/delete_user.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        DeleteUserController duController = fxmlLoader.getController();
        duController.setClient(client);
        duController.setStage(stage);
    }

    @FXML
    public void handleRegisterAdmin(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/register_admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        RegisterAdminController raController = fxmlLoader.getController();
        raController.setClient(client);
        raController.setStage(stage);
    }

    @FXML
    public void handleViewUser(ActionEvent event) throws Exception
    {
        client.sendObjToServer("VIEW_USER");

        User userItem;

        users = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            userItem = (User) client.getIn().readObject();
            users.add(userItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/view_user.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ViewUserController vuController = fxmlLoader.getController();
        vuController.setClient(client);
        vuController.setStage(stage);
    }
}
