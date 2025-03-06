package kp.control.PostControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.model.Post;
import kp.util.WarningBox;

public class AddPostController {
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
            WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
            return;
        }

        client.sendObjToServer("ADD_POST");

        String name = nameField.getText();
        String address = addressField.getText();

        Post post = new Post(name, address);

        client.sendObjToServer(post);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManagePostController mpController = fxmlLoader.getController();
        mpController.setClient(client);
        mpController.setStage(stage);
    }
}
