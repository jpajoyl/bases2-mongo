package app.mongooracle.bd.schemas;

import javafx.util.Pair;

public class EstadisticasDepartamentales {
    private String totalVentasDepartamento;
    private Pair<String,String> mejorCiudad;
    private Pair<String,String> mejorVendedor;
    private Pair<String,String> peorVendedor;



    public EstadisticasDepartamentales(int totalVentasDepartamento , String mejorCiudad, int mejorCiudadVentas, String mejorVendedor, int mejorVendedorVentas , String peorVendedor , int peorVendedorVentas)
    {
        this.totalVentasDepartamento = String.valueOf(totalVentasDepartamento);
        this.mejorCiudad = new Pair(mejorCiudad,String.valueOf(mejorCiudadVentas));
        this.mejorVendedor = new Pair(mejorVendedor,String.valueOf(mejorVendedorVentas));
        this.peorVendedor = new Pair(peorVendedor,String.valueOf(peorVendedorVentas));

    }

    @Override
    public String toString() {
        return "EstadisticasDepartamentales{" +
                "totalVentasDepartamento=" + totalVentasDepartamento +
                ", mejorCiudad=" + mejorCiudad +
                ", mejorVendedor=" + mejorVendedor +
                ", peorVendedor=" + peorVendedor +
                '}';
    }

    public String getTotalVentasDepartamento() {
        return totalVentasDepartamento;
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