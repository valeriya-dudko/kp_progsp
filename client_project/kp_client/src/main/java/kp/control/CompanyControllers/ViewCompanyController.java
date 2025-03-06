package kp.control.CompanyControllers;

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
import kp.control.PostControllers.ManagePostController;
import kp.model.Company;
import kp.model.Post;

public class ViewCompanyController {
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

    public void initialize()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<Company, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Company, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Company, String>("address"));

        compTable.getItems().setAll(ManageCompanyController.comps);
    }

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCompanyController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }
}
