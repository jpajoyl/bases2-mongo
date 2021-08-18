package net.codeJava;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ResultSet departamentos = Oracle.consulta("select COD, nom from departamento");
        while (departamentos.next()) {
            Document doc = new Document("_id", new ObjectId());
            int deptCode = departamentos.getInt("cod");
            String deptName = departamentos.getString("nom");
            doc.append("nombre", deptName);
            ResultSet ciudades = Oracle.consulta("select c.cod, c.nom from ciudad c where c.MIDEP.COD = " + deptCode);
            ArrayList<Document> ventasList = new ArrayList<Document>();
            int totalVentas = 0;
            while (ciudades.next()) {
                String nomCiudad = ciudades.getString("nom");
                int codCiudad = ciudades.getInt("cod");
                ResultSet vent = Oracle.consulta("select sum(total) as total from " +
                        "(select sum(v.nro_unidades * v.miprod.precio_unitario) " +
                        "as total from empleado e, TABLE(e.VENTAS) v where e.MICIU.COD = " +
                        codCiudad + ")");
                int totVentas = 0;
                if (vent.next()) {
                    totVentas = vent.getInt("total");
                    totalVentas += totVentas;
                }
                ResultSet mejorVendedor = Oracle.consulta("select *" +
                        "from (" +
                        "         select e.CC, sum(v.nro_unidades * v.miprod.precio_unitario) as total" +
                        "         from empleado e," +
                        "              TABLE (e.VENTAS) v" +
                        "         where e.MICIU.COD = " + codCiudad +
                        "         group by e.CC" +
                        "         order by total desc)" +
                        "where ROWNUM = 1");
                Integer ccMv = null;
                int totalVMV = 0;
                if (mejorVendedor.next()) {
                    ccMv = mejorVendedor.getInt("cc");
                    totalVMV = mejorVendedor.getInt("total");
                }
                Document venta = new Document().append("nombre_ciudad", nomCiudad)
                        .append("total_ventas", totVentas)
                        .append("mejor_vendedor", ccMv)
                        .append("ventas_mejor_vendedor", totalVMV);
                ventasList.add(venta);

            }
            doc.append("total_ventas", totalVentas);
            doc.append("misventas", ventasList);
            MongoCollection<Document> collection = MongoDb.getCollection();
            collection.insertOne(doc);
        }


    }
}
