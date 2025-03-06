package kp.control.UserControllers;

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
import kp.model.*;
import kp.util.WarningBox;

public class UnblockUserController {
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
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Long> idCol;
    @FXML
    private TableColumn<User, String> loginCol;
    @FXML
    private TableColumn<User, String> roleCol;

    @FXML
    private TextField idField;

    long maxId;
    long minId;

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        loginCol.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        roleCol.setCellValueFactory( cellData -> {
                    int role = cellData.getValue().getRole();
                    String roleAsString;
                    if(role == 1)
                    {
                        roleAsString = "старший администратор";
                    }
                    else if(role == 2)
                    {
                        roleAsString = "администратор";
                    }
                    else
                    {
                        roleAsString = "пользователь";
                    }

                    return new ReadOnlyStringWrapper(roleAsString);
                }
        );

        userTable.getItems().setAll(MainHadminController.users);

        maxId = MainHadminController.users.getLast().getId();
        minId = MainHadminController.users.getFirst().getId();
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
        client.sendObjToServer(id);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_hadmin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        MainHadminController mhaController = fxmlLoader.getController();
        mhaController.setClient(client);
        mhaController.setStage(stage);
    }
}
