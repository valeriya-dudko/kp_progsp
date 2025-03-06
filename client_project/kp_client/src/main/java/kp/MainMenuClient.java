package kp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kp.control.LoginController;
import kp.control.RegisterController;

import java.net.Socket;
import java.util.ArrayList;

public class MainMenuClient extends Application {
    private ArrayList<Thread> threads;
    private Stage stage;

    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @Override
    public void start(Stage stage) {
        try
        {
            //threads = new ArrayList<Thread>();
            this.stage = stage;
            Socket clientSocket = new Socket("127.0.0.1",2525);
            Client client = new Client(clientSocket);
            FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource("/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            LoginController loginController = fxmlLoader.getController();
            loginController.setClient(client);
            loginController.setStage(stage);

            stage.setTitle("Таможня");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void switchScenes(String fxmlString, Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuClient.class.getResource(fxmlString));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

    }



    public static void main(String[] args) {
        try
        {
            launch(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
