package kp.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.util.*;
import kp.model.*;

public class DeclareGoodsController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField rateField;
    @FXML
    private TextField VATField;
    @FXML
    private TextField exciseField;

    @FXML
    private RadioButton advalorButton;
    @FXML
    private RadioButton specificButton;
    @FXML
    private RadioButton combinedButton;
    @FXML
    private RadioButton importButton;
    @FXML
    private RadioButton exportButton;

    @FXML
    private ToggleGroup rate_type;
    @FXML
    private ToggleGroup direction;

    @FXML
    private Label VATLabel;
    @FXML
    private Label exciseLabel;

    private static Client client;
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
    public void handleImportButton(ActionEvent event)
    {
        VATField.setVisible(true);
        exciseField.setVisible(true);

        VATLabel.setVisible(true);
        exciseLabel.setVisible(true);
    }

    @FXML
    public void handleExportButton(ActionEvent event)
    {
        VATField.setVisible(false);
        exciseField.setVisible(false);//clear

        VATField.clear();
        exciseField.clear();//clear

        VATLabel.setVisible(false);
        exciseLabel.setVisible(false);
    }

    @FXML
    public void handleSendButton(ActionEvent event) throws Exception{
        Good good = new Good();
        String name = nameField.getText();

        if(nameField.getText().equals("") || amountField.getText().equals("") || weightField.getText().equals("")
        || priceField.getText().equals("") || rateField.getText().equals("") || rate_type.getSelectedToggle() == null
        || direction.getSelectedToggle() == null)
        {
            WarningBox.showWarningBox("Заполните все поля!");
            return;
        }

        try
        {
        client.sendObjToServer("DECLARE_GOODS");


        String amount = amountField.getText();
        String weight = weightField.getText();
        String price = priceField.getText();
        String rate = rateField.getText();
        String vat = ToNullConverter.emptyStringToNull(VATField.getText()) ;
        String excise = ToNullConverter.emptyStringToNull(exciseField.getText());

        boolean advalor = advalorButton.isSelected();
        boolean specific = specificButton.isSelected();
        boolean combined = combinedButton.isSelected();
        boolean importing = importButton.isSelected();
        boolean export = exportButton.isSelected();


        if(advalor)
        {good.setRateType(1);}
        else if(specific)
        {good.setRateType(2);}
        else if(combined)
        {good.setRateType(3);}

        if(importing)
        {good.setIsImport(true);}
        else if(export)
        {good.setIsImport(false);}


            good.setAmount(Integer.parseInt(amount));
            good.setWeight(Double.parseDouble(weight));
            good.setPrice(Double.parseDouble(price));
            good.setRate(Double.parseDouble(rate));


        if(vat != null)
        {
            good.setVAT(Integer.parseInt(vat));
        }
        if(excise != null)
        {
            good.setExcise(Double.parseDouble(excise));
        }
        }
        catch(Exception e)
        {
            WarningBox.showWarningBox("Некорректный ввод, повторите попытку.");
            client.sendObjToServer("ERROR");
            return;
        }
        client.sendObjToServer("OK");
        Declarant currDeclarant = (Declarant) client.getIn().readObject();


        good.setName(name);
        good.setDeclarant(currDeclarant);

        good.setIsConfirmed(false);

        client.sendObjToServer(good);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_user.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        MainUserController muController = fxmlLoader.getController();
        muController.setClient(client);
        muController.setStage(stage);

    }

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_user.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        MainUserController muController = fxmlLoader.getController();
        muController.setClient(client);
        muController.setStage(stage);
    }
}
