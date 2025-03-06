package kp.control;

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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainUserController {
    @FXML
    private Button editDeclarant;
    @FXML
    private Button addGood;
    @FXML
    private Button calculateTax;
    @FXML
    private Button outputTax;

    @FXML
    private Button logoutButton;

    private Client client;
    private Stage stage;
    private Double tax;

    static List<Good> goods;

    public void setClient(Client client)
    {
        this.client = client;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setTax(Double tax)
    {
        this.tax = tax;
    }

    @FXML
    public void handleLogout(ActionEvent event) throws Exception
    {
        String choice = "LOGOUT";
        client.sendObjToServer(choice);

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        LoginController loginController = fxmlLoader.getController();
        loginController.setClient(client);
        loginController.setStage(stage);
    }

    @FXML
    public void handleEditDeclarant(ActionEvent event) throws Exception
    {
        String choice = "EDIT_DECLARANT";
        client.sendObjToServer(choice);

        Company compItem;
        LoginController.allComp = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        System.out.println(arrSize);
        for(int i = 0; i < arrSize; i++)
        {
            compItem = (Company) client.getIn().readObject();
            LoginController.allComp.add(compItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/edit_declarant.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        EditDeclarantController edController = fxmlLoader.getController();
        edController.setClient(client);
        edController.setStage(stage);
    }

    @FXML
    public void handleDeclareGoods(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/declare_goods.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        DeclareGoodsController dgController = fxmlLoader.getController();
        dgController.setClient(client);
        dgController.setStage(stage);
    }

    @FXML
    public void handleCalcTax(ActionEvent event) throws Exception
    {
        client.sendObjToServer("TO_CALC_TAX");

        Integer arrSize = (Integer) client.getIn().readObject();
        //System.out.println(arrSize);
        goods = new ArrayList<Good>();
        Good goodItem;

        for(int i = 0; i < arrSize; i++)
        {
            goodItem = (Good) client.getIn().readObject();
            //System.out.println(goodItem.getName());
            goods.add(goodItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/calc_tax.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        CalcTaxController ctController = fxmlLoader.getController();
        ctController.setClient(client);
        ctController.setStage(stage);
        //ctController.setGoods(goods);

    }

    @FXML
    public void handleFileTax(ActionEvent event) throws Exception
    {
        try
        {
            File file = new File("tax.txt");
            if(file.createNewFile())
            {
                System.out.println("Создан новый файл.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            WarningBox.showWarningBox("Ошибка при создании файла!");
        }
        try
        {
            FileWriter writer = new FileWriter("tax.txt");
            writer.write("Сумма таможенных сборов: " + tax + " BYN.");
            writer.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Данные записаны в файл tax.txt.");
            alert.showAndWait();

        }
        catch (IOException e)
        {
            e.printStackTrace();
            WarningBox.showWarningBox("Ошибка при выводе в файл!");
        }

    }

}
