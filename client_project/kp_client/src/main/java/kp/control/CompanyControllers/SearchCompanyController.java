package kp.control.CompanyControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.PostControllers.ManagePostController;
import kp.util.WarningBox;

public class SearchCompanyController {
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
    private Label infoLabel;

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

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        if(nameField.getText().isEmpty())
        {
            WarningBox.showWarningBox("Заполните все поля!");
            return;
        }

        client.sendObjToServer("SEARCH_COMP");

        client.sendObjToServer(nameField.getText());
        String response = (String) client.getIn().readObject();

        infoLabel.setText(response);
    }
}
