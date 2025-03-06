package kp.control.PostControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.util.WarningBox;

import java.io.IOException;

public class SearchPostController {
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManagePostController mpController = fxmlLoader.getController();
        mpController.setClient(client);
        mpController.setStage(stage);
    }

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        if(nameField.getText().isEmpty())
        {
            WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
            return;
        }
        client.sendObjToServer("SEARCH_POST");

        client.sendObjToServer(nameField.getText());
        String response = (String) client.getIn().readObject();

        infoLabel.setText(response);
    }
}
