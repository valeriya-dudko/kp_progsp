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

public class ConfirmGoodController {
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
    private TableView<Good> goodTable;
    @FXML
    private TableColumn<Good, Long> idCol;
    @FXML
    private TableColumn<Good, String> nameCol;
    @FXML
    private TableColumn<Good, Integer> amountCol;
    @FXML
    private TableColumn<Good, Double> weightCol;
    @FXML
    private TableColumn<Good, Double> priceCol;
    @FXML
    private TableColumn<Good, String> directionCol;

    @FXML
    private TextField idField;

    private long maxId;

    private long minId;

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Good, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Good, String>("name"));
        amountCol.setCellValueFactory(new PropertyValueFactory<Good, Integer>("amount"));
        weightCol.setCellValueFactory(new PropertyValueFactory<Good, Double>("weight"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Good, Double>("price"));
        directionCol.setCellValueFactory( cellData -> {
                    boolean isImport = cellData.getValue().getIsImport();
                    String isImportAsString;
                    if(isImport == true)
                    {
                        isImportAsString = "импорт";
                    }
                    else
                    {
                        isImportAsString = "экспорт";
                    }

                    return new ReadOnlyStringWrapper(isImportAsString);
                }
        );

        goodTable.getItems().setAll(ManageGoodController.goods);

        maxId = ManageGoodController.goods.getLast().getId();
        minId = ManageGoodController.goods.getFirst().getId();
    }

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception
    {
        if(idField.getText().equals(""))
        {
            WarningBox.showWarningBox("Заполните все поля!");
        }

        long id;

        try
        {
           id = Long.parseLong(idField.getText());
        }
        catch(Exception e)
        {
            WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
            return;
        }

        if(id > maxId || id < minId)
        {
            WarningBox.showWarningBox("Некорректный ввод. Повторите попытку.");
            return;
        }
        client.sendObjToServer("OK");

        client.sendObjToServer(id);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageGoodController mgController = fxmlLoader.getController();
        mgController.setClient(client);
        mgController.setStage(stage);
    }

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        client.sendObjToServer("BACK");
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageGoodController mgController = fxmlLoader.getController();
        mgController.setClient(client);
        mgController.setStage(stage);
    }
}
