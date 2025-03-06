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

import java.util.ArrayList;
import java.sql.Date;

public class FilterPassingController {
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
    private DatePicker arrDate1;
    @FXML
    private DatePicker arrDate2;

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        if(arrDate1.getValue()==null || arrDate2.getValue()==null)
        {
            WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
            return;
        }

        client.sendObjToServer("FILTER_PASSING");

        Date arr1 = Date.valueOf(arrDate1.getValue()) ;
        Date arr2 = Date.valueOf(arrDate2.getValue());

        client.sendObjToServer(arr1);
        client.sendObjToServer(arr2);

        Passing passingItem;

        ManagePassingController.passings = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            passingItem = (Passing) client.getIn().readObject();
            ManagePassingController.passings.add(passingItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/view_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ViewPassingController vpController = fxmlLoader.getController();
        vpController.setClient(client);
        vpController.setStage(stage);
    }
}
