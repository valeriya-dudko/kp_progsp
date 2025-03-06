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
import kp.control.CompanyControllers.ManageCompanyController;
import kp.control.PostControllers.ManagePostController;
import kp.model.Carrier;
import kp.model.Company;
import kp.model.Post;
import kp.util.WarningBox;

public class DeleteCarrierController {
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
    private TextField idField;

    private long maxId;
    private long minId;

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Carrier, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Carrier, String>("name"));
        compCol.setCellValueFactory(new PropertyValueFactory<Carrier, Company>("company"));

        carrierTable.getItems().setAll(ManageCarrierController.carriers);

        maxId = ManageCarrierController.carriers.getLast().getId();
        minId = ManageCarrierController.carriers.getFirst().getId();
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

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCarrierController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }
}
