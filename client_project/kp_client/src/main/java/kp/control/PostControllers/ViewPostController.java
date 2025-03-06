package kp.control.PostControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.model.Post;

public class ViewPostController {
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

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Post, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Post, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Post, String>("address"));

        postTable.getItems().setAll(ManagePostController.posts);
    }

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
}
