package kp.control.PassingControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.model.*;

import java.sql.Date;

public class ViewPassingController {
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
    private TableView<Passing> passingTable;

    @FXML
    private TableColumn<Passing, Long> idCol;
    @FXML
    private TableColumn<Passing, Date> arrCol;
    @FXML
    private TableColumn<Passing, Date> depCol;
    @FXML
    private TableColumn<Passing, Post> postCol;
    @FXML
    private TableColumn<Passing, Carrier> carrierCol;

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Passing, Long>("id"));
        arrCol.setCellValueFactory(new PropertyValueFactory<Passing, Date>("arrivalDate"));
        depCol.setCellValueFactory(new PropertyValueFactory<Passing, Date>("departureDate"));
        postCol.setCellValueFactory(new PropertyValueFactory<Passing, Post>("post"));
        carrierCol.setCellValueFactory(new PropertyValueFactory<Passing, Carrier>("carrier"));

        passingTable.getItems().setAll(ManagePassingController.passings);
    }

    @FXML
    public void handleBackButton(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManagePassingController mpController = fxmlLoader.getController();
        mpController.setClient(client);
        mpController.setStage(stage);
    }
}
