package app.mongooracle;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class MenuPrincipalController {
    public Button close_button;

    Alert a = new Alert(Alert.AlertType.NONE);

    @FXML
    private void routeEstadisticas(ActionEvent event) throws IOException {
        App.setRoot("/views/estadisticas");
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void generarEstadisticos() {

        try {
            App.oracle.generarEstadisticas();
        } catch (Exception e) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Error" + e);
            a.show();
            return;
        }

        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Estadisticas generadas");
        a.show();
    }


    @FXML
    private void vaciarArreglos(){
        try {
            App.oracle.vaciarArreglos();
        } catch (Exception e) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Error" + e);
            a.show();
            return;
        }
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Arreglos vaciados");
        a.show();
    }

}
