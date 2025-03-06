package kp.control.CarrierControllers;

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
import kp.control.PassingControllers.ManagePassingController;
import kp.model.Carrier;
import kp.model.Company;
import kp.model.Passing;
import kp.model.Post;

import java.sql.Date;

public class ViewCarrierController {
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
    private TableView<Carrier> carrierTable;

    @FXML
    private TableColumn<Carrier, Long> idCol;
    @FXML
    private TableColumn<Carrier, String> nameCol;
    @FXML
    private TableColumn<Carrier, Company> compCol;

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Carrier, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Carrier, String>("name"));
        compCol.setCellValueFactory(new PropertyValueFactory<Carrier, Company>("company"));

        carrierTable.getItems().setAll(ManageCarrierController.carriers);
    }

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCarrierController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }
}
