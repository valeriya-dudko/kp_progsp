package kp.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.UserControllers.MainHadminController;
import kp.util.*;
import kp.model.*;

import java.util.ArrayList;
import java.util.List;

public class LoginController  {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private Client client;
    private Stage stage;
    static List<Company> allComp;

    public void setClient(Client client)
    {
        this.client = client;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public List<Company> getAllComp()
    {
        return allComp;
    }

    @FXML
    public void handleSendButton(ActionEvent event) {
        String choice = "LOGIN";
        String login = loginField.getText();
        String password = passwordField.getText();

        User user = new User();
        user.setLogin(login);
        byte[] hash_pass = HashPassword.getHash(password);
        user.setPassword(hash_pass);

        loginField.clear();
        passwordField.clear();

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
           WarningBox.showWarningBox("Неверные данные для входа или учетная запись заблокирована!");
       }
       else if(response.equals("OK"))
       {

           try
           {
               User currUser = (User)client.getIn().readObject();
               System.out.println(currUser.getRole());

               if(currUser.getRole() == 3)
               {
                   FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_user.fxml"));

                   Scene scene = new Scene(fxmlLoader.load());
                   stage.setScene(scene);

                   MainUserController muController = fxmlLoader.getController();
                   muController.setClient(client);
                   muController.setStage(stage);
               }
               else if(currUser.getRole() == 2)
               {
                   FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_admin.fxml"));

                   Scene scene = new Scene(fxmlLoader.load());
                   stage.setScene(scene);

                   MainAdminController maController = fxmlLoader.getController();
                   maController.setClient(client);
                   maController.setStage(stage);

               }
               else if(currUser.getRole() == 1)
               {
                   FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_hadmin.fxml"));

                   Scene scene = new Scene(fxmlLoader.load());
                   stage.setScene(scene);

                   MainHadminController mhaController = fxmlLoader.getController();
                   mhaController.setClient(client);
                   mhaController.setStage(stage);
               }


           } catch (Exception e) {
               e.printStackTrace();
           }
       }


    }

    @FXML
    public void handleToRegistration(ActionEvent event) throws Exception
    {
//        MainMenuClient menu = new MainMenuClient();
//        menu.switchScenes("/registration.fxml", stage);
        String choice = "PRE_REGISTER";
        client.sendObjToServer(choice);
        Company compItem;

        allComp = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            compItem = (Company) client.getIn().readObject();
            allComp.add(compItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        RegisterController registerController = fxmlLoader.getController();
        registerController.setClient(client);
        registerController.setStage(stage);
        //registerController.setAllComp(allComp);
    }

}
