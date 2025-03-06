package kp.util;

import javafx.scene.control.Alert;

public class WarningBox {
    public static void showWarningBox(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
