package kp.control.CompanyControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.PostControllers.ManagePostController;
import kp.model.Company;
import kp.model.Post;
import kp.util.WarningBox;

import java.io.IOException;

public class AddCompanyController {
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

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        if(nameField.getText().isEmpty() || addressField.getText().isEmpty())
        {
            WarningBox.showWarningBox("Заполните все поля!");
            return;
        }
        client.sendObjToServer("ADD_COMP");

        String name = nameField.getText();
        String address = addressField.getText();

        Company comp = new Company(name, address);

        client.sendObjToServer(comp);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCompanyController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCompanyController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }
}
