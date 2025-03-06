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
import kp.util.WarningBox;

import java.sql.Date;

public class UpdatePassingController {
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
    private TextField idField;

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

    private long maxId;
    private long minId;

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Passing, Long>("id"));
        arrCol.setCellValueFactory(new PropertyValueFactory<Passing, Date>("arrivalDate"));
        depCol.setCellValueFactory(new PropertyValueFactory<Passing, Date>("departureDate"));
        postCol.setCellValueFactory(new PropertyValueFactory<Passing, Post>("post"));
        carrierCol.setCellValueFactory(new PropertyValueFactory<Passing, Carrier>("carrier"));

        passingTable.getItems().setAll(ManagePassingController.passings);

        maxId = ManagePassingController.passings.getLast().getId();
        minId = ManagePassingController.passings.getFirst().getId();
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

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/editing_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        EditingPassingController epController = fxmlLoader.getController();
        epController.setClient(client);
        epController.setStage(stage);

        epController.setId(id);

    }
}
