package kp.control.CarrierControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.PassingControllers.ManagePassingController;
import kp.model.Carrier;
import kp.model.Company;
import kp.model.Passing;
import kp.model.Post;

import java.sql.Date;

public class AddCarrierController {
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
    private ChoiceBox<Company> compChoice;
    @FXML
    private TextField nameField;

    public void initialize()
    {
        compChoice.getItems().addAll(ManageCarrierController.comps);
    }

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        Company comp = compChoice.getValue();
        String name = nameField.getText();
        Carrier carrier = new Carrier();

        carrier.setCompany(comp);
        carrier.setName(name);

        client.sendObjToServer(carrier);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCarrierController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }
}
