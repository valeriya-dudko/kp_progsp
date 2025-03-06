package kp.control;

import javafx.beans.property.ReadOnlyStringWrapper;
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
import kp.model.Good;

public class ViewGoodController {
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

    }

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageGoodController mgController = fxmlLoader.getController();
        mgController.setClient(client);
        mgController.setStage(stage);
    }
}
