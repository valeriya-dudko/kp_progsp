package kp.control.PassingControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.model.*;
import kp.util.WarningBox;

import java.sql.Date;

public class AddPassingController {
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
    private ChoiceBox<Post> postChoice;
    @FXML
    private ChoiceBox<Carrier> carrierChoice;
    @FXML
    private DatePicker arrPicker;
    @FXML
    private DatePicker depPicker;

    public void initialize()
    {
        postChoice.getItems().addAll(ManagePassingController.posts);
        carrierChoice.getItems().addAll(ManagePassingController.carriers);
    }

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        if(postChoice.getValue() == null || carrierChoice.getValue() == null
                || arrPicker.getValue() == null || depPicker.getValue() == null)
        {
            WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
            return;
        }

        client.sendObjToServer("ADD_PASSING");

        Date arrDate = Date.valueOf(arrPicker.getValue()) ;
        Date depDate = Date.valueOf(depPicker.getValue());
        Post post = postChoice.getValue();
        Carrier carrier = carrierChoice.getValue();

        Passing passing = new Passing();
        passing.setCarrier(carrier);
        passing.setPost(post);
        passing.setArrivalDate(arrDate);
        passing.setDepartureDate(depDate);

        client.sendObjToServer(passing);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManagePassingController mpController = fxmlLoader.getController();
        mpController.setClient(client);
        mpController.setStage(stage);
    }
}
