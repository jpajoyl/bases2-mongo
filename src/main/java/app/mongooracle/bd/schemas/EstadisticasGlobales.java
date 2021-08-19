package app.mongooracle.bd.schemas;

import javafx.util.Pair;

public class EstadisticasGlobales {
    private Pair<String,String> mejorDepartamento;
    private Pair<String,String> mejorCiudad;
    private Pair<String,String> mejorVendedor;
    private Pair<String,String> peorVendedor;

    public EstadisticasGlobales(String mejorDepartamento, int mejorDepartamentoVentas , String mejorCiudad, int mejorCiudadVentas, String mejorVendedor, int mejorVendedorVentas , String peorVendedor , int peorVendedorVentas)
    {
        this.mejorDepartamento = new Pair(mejorDepartamento,String.valueOf(mejorDepartamentoVentas));
        this.mejorCiudad = new Pair(mejorCiudad,String.valueOf(mejorCiudadVentas));
        this.mejorVendedor = new Pair(mejorVendedor,String.valueOf(mejorVendedorVentas));
        this.peorVendedor = new Pair(peorVendedor,String.valueOf(peorVendedorVentas));

    }

    @Override
    public String toString() {
        return "EstadisticasGlobales{" +
                "mejorDepartamento=" + mejorDepartamento +
                ", mejorCiudad=" + mejorCiudad +
                ", mejorVendedor=" + mejorVendedor +
                ", peorVendedor=" + peorVendedor +
                '}';
    }

    public Pair<String, String> getMejorDepartamento() {
        return mejorDepartamento;
    }

    public Pair<String, String> getMejorCiudad() {
        return mejorCiudad;
    }

    public Pair<String, String> getMejorVendedor() {
        return mejorVendedor;
    }

    public Pair<String, String> getPeorVendedor() {
        return peorVendedor;
    }
}