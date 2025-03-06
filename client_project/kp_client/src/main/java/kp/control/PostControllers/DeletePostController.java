package kp.control.PostControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.UserControllers.MainHadminController;
import kp.model.Post;
import kp.util.WarningBox;

public class DeletePostController {
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
    private TableView<Post> postTable;

    @FXML
    private TableColumn<Post, Long> idCol;
    @FXML
    private TableColumn<Post, String> nameCol;
    @FXML
    private TableColumn<Post, String> addressCol;

    @FXML
    private TextField idField;

    private long maxId;
    private long minId;

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Post, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Post, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Post, String>("address"));

        postTable.getItems().setAll(ManagePostController.posts);

        maxId = ManagePostController.posts.getLast().getId();
        minId = ManagePostController.posts.getFirst().getId();
    }

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        long id;

        try
        {
            id = Long.parseLong(idField.getText());
            if(id > maxId || id < minId)
            {
                WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
                return;
            }
        }
        catch(Exception e)
        {
            WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
            return;
        }
        client.sendObjToServer(id);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManagePostController mpController = fxmlLoader.getController();
        mpController.setClient(client);
        mpController.setStage(stage);
    }
}
