package net.codeJava;

import java.sql.*;

public class Oracle {
    public static void main(String[] args) {
        Connection conn;
        Statement sentencia;
        ResultSet resultado;
        System.out.println("Conexión a la base de datos");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver JDBC");
            return;
        }
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "juanma");
            sentencia = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("No hay conexión con la base de datos.");
            return;
        }
        try {
            System.out.println("Seleccionando...");
            resultado = sentencia.executeQuery("SELECT * FROM departamento");
            //Se recorren las tuplas retornadas
            while (resultado.next()) {
                System.out.println(resultado.getInt("cod") +
                        "---" + resultado.getString("nom"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Consulta finalizada.");
    }
}
