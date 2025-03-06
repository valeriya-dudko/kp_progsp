package kp.control.PassingControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.MainAdminController;
import kp.model.Carrier;
import kp.model.Passing;
import kp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class ManagePassingController {
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
    static List<Carrier> carriers;
    static List<Passing> passings;

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
    public void handleAddPassing(ActionEvent event) throws Exception
    {
        client.sendObjToServer("TO_ADD_PASSING");

        Post postItem;

        posts = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            postItem = (Post) client.getIn().readObject();
            posts.add(postItem);
        }

        Carrier carrierItem;

        carriers = new ArrayList<>();

        arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            carrierItem = (Carrier) client.getIn().readObject();
            carriers.add(carrierItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/add_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        AddPassingController addPassingController = fxmlLoader.getController();
        addPassingController.setClient(client);
        addPassingController.setStage(stage);
    }

    @FXML
    public void handleViewPassing(ActionEvent event) throws Exception
    {
        client.sendObjToServer("VIEW_PASSING");

        Passing passingItem;

        passings = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            passingItem = (Passing) client.getIn().readObject();
            passings.add(passingItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/view_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ViewPassingController vpController = fxmlLoader.getController();
        vpController.setClient(client);
        vpController.setStage(stage);
    }

    @FXML
    public void handleUpdatePassing(ActionEvent event) throws Exception
    {
        client.sendObjToServer("UPDATE_PASSING");

        Passing passingItem;

        passings = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            passingItem = (Passing) client.getIn().readObject();
            passings.add(passingItem);
        }

        //////
        Post postItem;

        posts = new ArrayList<>();

        arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            postItem = (Post) client.getIn().readObject();
            posts.add(postItem);
        }

        Carrier carrierItem;

        carriers = new ArrayList<>();

        arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            carrierItem = (Carrier) client.getIn().readObject();
            carriers.add(carrierItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/update_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        UpdatePassingController upController = fxmlLoader.getController();
        upController.setClient(client);
        upController.setStage(stage);
    }

    @FXML
    public void handleDeletePassing(ActionEvent event) throws Exception
    {
        client.sendObjToServer("TO_DELETE_PASSING");

        Passing passingItem;

        passings = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            passingItem = (Passing) client.getIn().readObject();
            passings.add(passingItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/delete_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        DeletePassingController dpController = fxmlLoader.getController();
        dpController.setClient(client);
        dpController.setStage(stage);
    }

    @FXML
    public void handleFilterPassing(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/filter_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        FilterPassingController fpController = fxmlLoader.getController();
        fpController.setClient(client);
        fpController.setStage(stage);
    }
}
