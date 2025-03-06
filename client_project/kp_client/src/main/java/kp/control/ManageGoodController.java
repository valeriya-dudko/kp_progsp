package kp.control;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.util.*;
import kp.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageGoodController {
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

    static List<Good> goods;
    static List<Declarant> declarants;

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
    public void handleViewGood(ActionEvent event) throws Exception
    {
        client.sendObjToServer("VIEW_GOOD");

        Good goodItem;

        goods = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            goodItem = (Good) client.getIn().readObject();
            goods.add(goodItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/view_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ViewGoodController vgController = fxmlLoader.getController();
        vgController.setClient(client);
        vgController.setStage(stage);
    }

    @FXML
    public void handleConfirmGood(ActionEvent event) throws Exception
    {
        client.sendObjToServer("CONFIRM_GOOD");

        Good goodItem;

        goods = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            goodItem = (Good) client.getIn().readObject();
            goods.add(goodItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/confirm_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ConfirmGoodController cgController = fxmlLoader.getController();
        cgController.setClient(client);
        cgController.setStage(stage);
    }

    @FXML
    public void handleDiagramGood(ActionEvent event) throws Exception
    {
        client.sendObjToServer("DIAGRAM_GOOD");

        Good goodItem;

        goods = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            goodItem = (Good) client.getIn().readObject();
            goods.add(goodItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/diagram_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        DiagramGoodController dgController = fxmlLoader.getController();
        dgController.setClient(client);
        dgController.setStage(stage);
    }
}
