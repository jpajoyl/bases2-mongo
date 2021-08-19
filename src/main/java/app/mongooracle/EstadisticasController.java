package app.mongooracle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import app.mongooracle.bd.schemas.*;
import javafx.scene.text.Text;

public class EstadisticasController implements Initializable {
    @FXML
    public ComboBox departamentos_combo_box;

    @FXML
    public Text mejor_departamento_glob_text;

    @FXML
    public Text mejor_ciudad_glob_text;

    @FXML
    public Text mejor_vendedor_glob_text;
    @FXML
    public Text peor_vendedor_glob_text;

    @FXML
    public Text ventas_mejor_departamento_glob_text;
    @FXML
    public Text ventas_mejor_ciudad_glob_text;

    @FXML
    public Text ventas_mejor_vendedor_glob_text;
    @FXML
    public Text ventas_peor_vendedor_glob_text;

    @FXML
    public Text ventas_por_departamento_text;

    @FXML
    public Text mejor_ciudad_dep_text;
    @FXML
    public Text mejor_vendedor_dep_text;

    @FXML
    public Text peor_vendedor_dep_text;
    @FXML
    public Text ventas_mejor_ciudad_dep_text;

    @FXML
    public Text ventas_mejor_vendedor_dep_text;
    @FXML
    public Text ventas_peor_vendedor_dep_text;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> departamentos = App.mongo.getNombresDepartamentos();
        departamentos_combo_box.setItems(FXCollections.observableList(departamentos));

        EstadisticasGlobales estadisticasGlobales = App.mongo.getEstadisticasGlobales();
        mejor_departamento_glob_text.setText(estadisticasGlobales.getMejorDepartamento().getKey());
        ventas_mejor_departamento_glob_text.setText(estadisticasGlobales.getMejorDepartamento().getValue());

        mejor_ciudad_glob_text.setText(estadisticasGlobales.getMejorCiudad().getKey());
        ventas_mejor_ciudad_glob_text.setText(estadisticasGlobales.getMejorCiudad().getValue());

        mejor_vendedor_glob_text.setText(estadisticasGlobales.getMejorVendedor().getKey());
        ventas_mejor_vendedor_glob_text.setText(estadisticasGlobales.getMejorVendedor().getValue());

        peor_vendedor_glob_text.setText(estadisticasGlobales.getPeorVendedor().getKey());
        ventas_peor_vendedor_glob_text.setText(estadisticasGlobales.getPeorVendedor().getValue());
    }

    @FXML
    private void comboBoxEstadisticasDepartamento(ActionEvent event) throws IOException {
        String departamento = String.valueOf(departamentos_combo_box.getValue());

        EstadisticasDepartamentales estadisticasDepartamentales = App.mongo.getEstadisticasDepartamentales(departamento);
        ventas_por_departamento_text.setText("Total vendido: " + estadisticasDepartamentales.getTotalVentasDepartamento());

        mejor_ciudad_dep_text.setText(estadisticasDepartamentales.getMejorCiudad().getKey());
        ventas_mejor_ciudad_dep_text.setText(estadisticasDepartamentales.getMejorCiudad().getValue());

        mejor_vendedor_dep_text.setText(estadisticasDepartamentales.getMejorVendedor().getKey());
        ventas_mejor_vendedor_dep_text.setText(estadisticasDepartamentales.getMejorVendedor().getValue());

        peor_vendedor_dep_text.setText(estadisticasDepartamentales.getPeorVendedor().getKey());
        ventas_peor_vendedor_dep_text.setText(estadisticasDepartamentales.getPeorVendedor().getValue());

    }


    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("/views/menu_principal");
    }

}
