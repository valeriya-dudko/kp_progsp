package kp.control.CarrierControllers;

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
import kp.model.Carrier;
import kp.model.Company;
import kp.util.WarningBox;

import java.util.ArrayList;

public class SearchCarrierController {
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

    @FXML
    private TextField nameField;

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

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        if(nameField.getText().isEmpty())
        {
            WarningBox.showWarningBox("Заполните все поля!");
            return;
        }

        client.sendObjToServer("SEARCH_CARRIER");

        client.sendObjToServer(nameField.getText());

        Carrier carrItem;

        ManageCarrierController.carriers = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            carrItem = (Carrier) client.getIn().readObject();
            ManageCarrierController.carriers.add(carrItem);
        }

        idCol.setCellValueFactory(new PropertyValueFactory<Carrier, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Carrier, String>("name"));
        compCol.setCellValueFactory(new PropertyValueFactory<Carrier, Company>("company"));

        carrierTable.getItems().setAll(ManageCarrierController.carriers);
    }
}
