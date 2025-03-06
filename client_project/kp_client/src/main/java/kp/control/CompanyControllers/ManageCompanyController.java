package kp.control.CompanyControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;
import kp.control.MainAdminController;
import kp.control.PostControllers.*;
import kp.model.Carrier;
import kp.model.Company;
import kp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class ManageCompanyController {
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

    static List<Company> comps;

    @FXML
    public void handleBack(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/main_admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        MainAdminController maController = fxmlLoader.getController();
        maController.setClient(client);
        maController.setStage(stage);
    }

    @FXML
    public void handleAddComp(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/add_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        AddCompanyController acController = fxmlLoader.getController();
        acController.setClient(client);
        acController.setStage(stage);
    }

    @FXML
    public void handleViewComp(ActionEvent event) throws Exception
    {
        client.sendObjToServer("VIEW_COMP");

        Company compItem;

        comps = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            compItem = (Company) client.getIn().readObject();
            comps.add(compItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/view_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ViewCompanyController vcController = fxmlLoader.getController();
        vcController.setClient(client);
        vcController.setStage(stage);
    }

    @FXML
    public void handleEditComp(ActionEvent event) throws Exception
    {
        client.sendObjToServer("EDIT_COMP");

        Company compItem;

        comps = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            compItem = (Company) client.getIn().readObject();
            comps.add(compItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/edit_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        EditCompanyController ecController = fxmlLoader.getController();
        ecController.setClient(client);
        ecController.setStage(stage);
    }

    @FXML
    public void handleDeleteComp(ActionEvent event) throws Exception
    {
        client.sendObjToServer("DELETE_COMP");

        Company compItem;

        comps = new ArrayList<>();

        Integer arrSize = (Integer) client.getIn().readObject();
        for(int i = 0; i < arrSize; i++)
        {
            compItem = (Company) client.getIn().readObject();
            comps.add(compItem);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/delete_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        DeleteCompanyController dcController = fxmlLoader.getController();
        dcController.setClient(client);
        dcController.setStage(stage);
    }

    @FXML
    public void handleSearchComp(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/search_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        SearchCompanyController scController = fxmlLoader.getController();
        scController.setClient(client);
        scController.setStage(stage);
    }
}
