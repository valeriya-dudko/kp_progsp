package kp.control.CarrierControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.MainAdminController;
import kp.control.PostControllers.*;
import kp.model.Carrier;
import kp.model.Company;
import kp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class ManageCarrierController {
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

    static List<Carrier> carriers;
    static List<Company> comps;

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
    public void handleAddCarrier(ActionEvent event) throws Exception
    {
        client.sendObjToServer("ADD_CARRIER");

        Company compItem;

        comps = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            compItem = (Company) client.getIn().readObject();
            comps.add(compItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/add_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        AddCarrierController acController = fxmlLoader.getController();
        acController.setClient(client);
        acController.setStage(stage);
    }

    @FXML
    public void handleViewCarrier(ActionEvent event) throws Exception
    {
        client.sendObjToServer("VIEW_CARRIER");

        Carrier carrItem;

        carriers = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            carrItem = (Carrier) client.getIn().readObject();
            carriers.add(carrItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/view_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ViewCarrierController vcController = fxmlLoader.getController();
        vcController.setClient(client);
        vcController.setStage(stage);
    }

    @FXML
    public void handleEditCarrier(ActionEvent event) throws Exception
    {
        client.sendObjToServer("EDIT_CARRIER");

        Carrier carrItem;

        carriers = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            carrItem = (Carrier) client.getIn().readObject();
            carriers.add(carrItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/edit_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        EditCarrierController ecController = fxmlLoader.getController();
        ecController.setClient(client);
        ecController.setStage(stage);
    }

    @FXML
    public void handleDeleteCarrier(ActionEvent event) throws Exception
    {
        client.sendObjToServer("DELETE_CARRIER");

        Carrier carrItem;

        carriers = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            carrItem = (Carrier) client.getIn().readObject();
            carriers.add(carrItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/delete_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        DeleteCarrierController dcController = fxmlLoader.getController();
        dcController.setClient(client);
        dcController.setStage(stage);
    }

    @FXML
    public void handleSearchCarrier(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/search_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        SearchCarrierController scController = fxmlLoader.getController();
        scController.setClient(client);
        scController.setStage(stage);
    }
}
