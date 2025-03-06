package kp.control.CompanyControllers;

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
import kp.control.PostControllers.ManagePostController;
import kp.model.Company;
import kp.model.Post;
import kp.util.WarningBox;

public class DeleteCompanyController {
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
    private TableView<Company> compTable;

    @FXML
    private TableColumn<Company, Long> idCol;
    @FXML
    private TableColumn<Company, String> nameCol;
    @FXML
    private TableColumn<Company, String> addressCol;

    @FXML
    private TextField idField;

    private long maxId;
    private long minId;

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Company, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Company, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Company, String>("address"));

        compTable.getItems().setAll(ManageCompanyController.comps);

        maxId = ManageCompanyController.comps.getLast().getId();
        minId = ManageCompanyController.comps.getFirst().getId();
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

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCompanyController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }
}
