package kp.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.util.*;
import kp.model.*;

public class EditDeclarantController {
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<Company> compChoice;

    private static Client client;
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
    public void handleSendButton(ActionEvent event) throws Exception{
        if(nameField.getText().equals("") || compChoice.getSelectionModel().getSelectedItem() == null)
        {
            WarningBox.showWarningBox("Заполните все поля!");
        }

        String name = nameField.getText();
        Company company = compChoice.getValue();

        Declarant decl = new Declarant();
        decl.setName(name);
        decl.setCompany(company);
        System.out.println(company.getId());

        client.sendObjToServer(decl);

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
            WarningBox.showWarningBox("Проверьте правильность данных.");
        }
        else if(response.equals("OK"))
        {
            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_user.fxml"));

                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);

                MainUserController muController = fxmlLoader.getController();
                muController.setClient(client);
                muController.setStage(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
