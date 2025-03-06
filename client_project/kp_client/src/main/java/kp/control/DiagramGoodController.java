package kp.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.util.*;
import kp.model.*;

public class DiagramGoodController {
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
    private PieChart goodChart;

    @FXML
    public void handleBackButton(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageGoodController mgController = fxmlLoader.getController();
        mgController.setClient(client);
        mgController.setStage(stage);
    }

    public void initialize() throws Exception
    {
        int countImport = 0;
        int countExport = 0;

        for(Good good: ManageGoodController.goods)
        {
            if(good.getIsImport())
            {
                countImport += 1;
            }
            else
            {
                countExport += 1;
            }
        }

        ObservableList<PieChart.Data> pcd = FXCollections.observableArrayList(
                new PieChart.Data("Импорт",countImport),
                new PieChart.Data("Экспорт", countExport));

        goodChart.setData(pcd);
        goodChart.setLegendSide(Side.LEFT);
    }

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageGoodController mgController = fxmlLoader.getController();
        mgController.setClient(client);
        mgController.setStage(stage);
    }
}
