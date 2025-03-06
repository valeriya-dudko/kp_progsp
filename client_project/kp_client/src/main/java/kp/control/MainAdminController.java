package kp.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kp.Client;
import kp.MainMenuClient;

import kp.control.CarrierControllers.ManageCarrierController;
import kp.control.CompanyControllers.ManageCompanyController;
import kp.control.PassingControllers.ManagePassingController;
import kp.control.PostControllers.ManagePostController;

public class MainAdminController {
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
    public void handleManagePassing(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_passing.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManagePassingController mpController = fxmlLoader.getController();
        mpController.setClient(client);
        mpController.setStage(stage);
    }

    @FXML
    public void handleManageGood(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_good.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageGoodController mgController = fxmlLoader.getController();
        mgController.setClient(client);
        mgController.setStage(stage);
    }

    @FXML
    public void handleManagePost(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_post.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManagePostController mpController = fxmlLoader.getController();
        mpController.setClient(client);
        mpController.setStage(stage);
    }

    @FXML
    public void handleManageCarrier(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_carrier.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCarrierController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }

    @FXML
    public void handleManageCompany(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/manage_company.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        ManageCompanyController mcController = fxmlLoader.getController();
        mcController.setClient(client);
        mcController.setStage(stage);
    }

}
