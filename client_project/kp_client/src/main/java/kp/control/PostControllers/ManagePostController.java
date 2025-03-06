package kp.control.PostControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.MainAdminController;
import kp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class ManagePostController {
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

    static List<Post> posts;

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        MainAdminController maController = fxmlLoader.getController();
        maController.setClient(client);
        maController.setStage(stage);
    }

    @FXML
    public void handleAddPost(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/add_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        AddPostController apController = fxmlLoader.getController();
        apController.setClient(client);
        apController.setStage(stage);
    }

    @FXML
    public void handleViewPost(ActionEvent event) throws Exception
    {
        client.sendObjToServer("VIEW_POST");

        Post postItem;

        posts = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            postItem = (Post) client.getIn().readObject();
            posts.add(postItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/view_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ViewPostController vpController = fxmlLoader.getController();
        vpController.setClient(client);
        vpController.setStage(stage);
    }

    @FXML
    public void handleEditPost(ActionEvent event) throws Exception
    {
        client.sendObjToServer("EDIT_POST");

        Post postItem;

        posts = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            postItem = (Post) client.getIn().readObject();
            posts.add(postItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/edit_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        EditPostController epController = fxmlLoader.getController();
        epController.setClient(client);
        epController.setStage(stage);
    }

    @FXML
    public void handleDeletePost(ActionEvent event) throws Exception
    {
        client.sendObjToServer("DELETE_POST");

        Post postItem;

        posts = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            postItem = (Post) client.getIn().readObject();
            posts.add(postItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/delete_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        DeletePostController dpController = fxmlLoader.getController();
        dpController.setClient(client);
        dpController.setStage(stage);
    }

    @FXML
    public void handleSearchPost(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/search_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        SearchPostController spController = fxmlLoader.getController();
        spController.setClient(client);
        spController.setStage(stage);
    }
}
