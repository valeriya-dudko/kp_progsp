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

import java.io.IOException;
import java.util.List;

public class CalcTaxController {
    @FXML
    private Label taxLabel;

    @FXML
    private TableView<Good> goodTable;
    @FXML
    private TableColumn<Good, String> nameCol;
    @FXML
    private TableColumn<Good, Integer> amountCol;
    @FXML
    private TableColumn<Good, Double> weightCol;
    @FXML
    private TableColumn<Good, Double> priceCol;
    @FXML
    private TableColumn<Good, Double> rateCol;
    @FXML
    private TableColumn<Good, Double> exciseCol;
    @FXML
    private TableColumn<Good, Integer> vatCol;
    @FXML
    private TableColumn<Good, String> directionCol;

    private Client client;
    private Stage stage;
    private List<Good> goods;
    private Double tax;

    public void setClient(Client client)
    {
        this.client = client;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setGoods(List<Good> goods)
    {
        this.goods = goods;
    }

    public void initialize()
    {
        nameCol.setCellValueFactory(new PropertyValueFactory<Good, String>("name"));
        amountCol.setCellValueFactory(new PropertyValueFactory<Good, Integer>("amount"));
        weightCol.setCellValueFactory(new PropertyValueFactory<Good, Double>("weight"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Good, Double>("price"));
        rateCol.setCellValueFactory(new PropertyValueFactory<Good, Double>("rate"));
        exciseCol.setCellValueFactory(new PropertyValueFactory<Good, Double>("excise"));
        vatCol.setCellValueFactory(new PropertyValueFactory<Good, Integer>("VAT"));
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

        goodTable.getItems().setAll(MainUserController.goods);

    }

    @FXML
    public void handleBackButton(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_user.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        MainUserController muController = fxmlLoader.getController();
        muController.setClient(client);
        muController.setStage(stage);
        muController.setTax(tax);

    }

    @FXML
    public void handleCalcButton(ActionEvent event) throws Exception
    {
        client.sendObjToServer("CALC_TAX");
        tax = (Double) client.getIn().readObject();
        taxLabel.setText("Итого: " + tax.toString() + " BYN");
    }
}
