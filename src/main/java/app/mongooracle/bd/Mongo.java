package app.mongooracle.bd;

import app.mongooracle.bd.schemas.EstadisticasDepartamentales;
import app.mongooracle.bd.schemas.EstadisticasGlobales;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Sorts.descending;


public class Mongo {

    private MongoDatabase db;

    public Mongo(String conectionUrl,String dbName) {
        MongoClient client = MongoClients.create(conectionUrl);
        this.db = client.getDatabase(dbName);
    }

    public MongoCollection<Document> getCollection() {
        return db.getCollection("departamentos");
    }

    public void dropCollection(){
        MongoCollection<Document> departamentos = this.getCollection();
        departamentos.drop();
    }

    public ArrayList<String> getNombresDepartamentos(){
        ArrayList<String> departamentos = new ArrayList<>();
        MongoCollection<Document> collectionDepartamentos = db.getCollection("departamentos");
        collectionDepartamentos.find().forEach(document -> departamentos.add(document.get("nombre").toString()));
        return departamentos;
    }

    public EstadisticasGlobales getEstadisticasGlobales(){
        MongoCollection<Document> collectionDepartamentos = db.getCollection("departamentos");
        Document mejorDepartamento = collectionDepartamentos.find().sort(descending("total_ventas")).first();
        Bson filter = Filters.not(Filters.eq("misventas.mejor_vendedor",null));



        int totalVentasMejorDepartamento = 0;
        String nombreMejorDepartamento = "";
        if (mejorDepartamento != null){
            nombreMejorDepartamento = mejorDepartamento.getString("nombre");
            totalVentasMejorDepartamento = mejorDepartamento.getInteger("total_ventas");
        }
        if (totalVentasMejorDepartamento == 0) {
            nombreMejorDepartamento = "No hay";
        }

        Document mejorCiudad = collectionDepartamentos.aggregate(Arrays.asList(
                Aggregates.match(filter),Aggregates.unwind("$misventas"),Aggregates.sort(Sorts.descending("misventas.total_ventas"))
        )).first();

        String nombreMejorCiudad = "No tiene";
        int totalVentasMejorCiudad = 0;

        if (mejorCiudad != null){
            Document infoVenta = (Document) mejorCiudad.get("misventas");
            nombreMejorCiudad = infoVenta.getString("nombre_ciudad");
            totalVentasMejorCiudad = infoVenta.getInteger("total_ventas");
        }

        Document mejorVendedor = collectionDepartamentos.aggregate(Arrays.asList(
                Aggregates.match(filter),Aggregates.unwind("$misventas"),Aggregates.sort(Sorts.descending("misventas.ventas_mejor_vendedor"))
        )).first();

        String cedulaMejorVendedor = "No hay";
        int totalVentasMejorVendedor = 0;

        if (mejorVendedor != null ){
            Document infoVenta = (Document) mejorVendedor.get("misventas");
            cedulaMejorVendedor = String.valueOf(infoVenta.getInteger("mejor_vendedor"));
            cedulaMejorVendedor = cedulaMejorVendedor.equals("null") ? "No hay" : cedulaMejorVendedor;
            totalVentasMejorVendedor = infoVenta.getInteger("ventas_mejor_vendedor");
        }

        Document peorVendedor = collectionDepartamentos.aggregate(Arrays.asList(
                Aggregates.match(filter),Aggregates.unwind("$misventas"),Aggregates.sort(Sorts.ascending("misventas.ventas_mejor_vendedor"))
        )).first();

        String cedulaPeorVendedor = "No hay";
        int totalPeorVendedor = 0;

        if (peorVendedor != null){
            Document infoVenta = (Document) peorVendedor.get("misventas");
            cedulaPeorVendedor = String.valueOf(infoVenta.getInteger("mejor_vendedor"));
            cedulaPeorVendedor = cedulaPeorVendedor.equals("null") ? "No hay" : cedulaPeorVendedor;
            totalPeorVendedor = infoVenta.getInteger("ventas_mejor_vendedor");
        }

        if (cedulaPeorVendedor.equals(cedulaMejorVendedor)){
            cedulaPeorVendedor = "No hay";
            totalPeorVendedor = 0;
        }

        EstadisticasGlobales estadisticasGlobales = new EstadisticasGlobales(nombreMejorDepartamento,totalVentasMejorDepartamento,nombreMejorCiudad,totalVentasMejorCiudad,cedulaMejorVendedor,totalVentasMejorVendedor,cedulaPeorVendedor,totalPeorVendedor);

        return estadisticasGlobales;
    }

    public EstadisticasDepartamentales getEstadisticasDepartamentales(String departamento){
        MongoCollection<Document> collectionDepartamentos = db.getCollection("departamentos");

        Bson con_vendedores_filter = Filters.not(Filters.eq("misventas.mejor_vendedor",null));
        Bson departamento_filter = Filters.eq("nombre",departamento);

        Document documentoDepartamento = collectionDepartamentos.find(departamento_filter).first();

        int totalVentasDepartamento = documentoDepartamento.getInteger("total_ventas");

        Document mejorCiudad = collectionDepartamentos.aggregate(Arrays.asList(
                Aggregates.match(departamento_filter),Aggregates.unwind("$misventas"),Aggregates.match(con_vendedores_filter),Aggregates.sort(Sorts.descending("misventas.total_ventas"))
        )).first();


        String nombreMejorCiudad = "No tiene";
        int totalVentasMejorCiudad = 0;

        if (mejorCiudad != null){
            Document infoVenta = (Document) mejorCiudad.get("misventas");
            nombreMejorCiudad = infoVenta.getString("nombre_ciudad");
            totalVentasMejorCiudad = infoVenta.getInteger("total_ventas");
        }


        Document mejorVendedor = collectionDepartamentos.aggregate(Arrays.asList(
                Aggregates.match(departamento_filter),Aggregates.unwind("$misventas"),Aggregates.match(con_vendedores_filter),Aggregates.sort(Sorts.descending("misventas.ventas_mejor_vendedor"))
        )).first();


        String cedulaMejorVendedor = "No tiene";
        int totalVentasMejorVendedor = 0;

        if (mejorVendedor != null ){
            Document infoVenta = (Document) mejorVendedor.get("misventas");
            cedulaMejorVendedor = String.valueOf(infoVenta.getInteger("mejor_vendedor"));
            cedulaMejorVendedor = cedulaMejorVendedor.equals("null") ? "No tiene" : cedulaMejorVendedor;
            totalVentasMejorVendedor = infoVenta.getInteger("ventas_mejor_vendedor");
        }




        Document peorVendedor = collectionDepartamentos.aggregate(Arrays.asList(
                Aggregates.match(departamento_filter),Aggregates.unwind("$misventas"),Aggregates.match(con_vendedores_filter),Aggregates.sort(Sorts.ascending("misventas.ventas_mejor_vendedor"))
        )).first();

        String cedulaPeorVendedor = "No tiene";
        int totalPeorVendedor = 0;

        if (peorVendedor != null){
            Document infoVenta = (Document) peorVendedor.get("misventas");
            cedulaPeorVendedor = String.valueOf(infoVenta.getInteger("mejor_vendedor"));
            cedulaPeorVendedor = cedulaPeorVendedor.equals("null") ? "No tiene" : cedulaPeorVendedor;
            totalPeorVendedor = infoVenta.getInteger("ventas_mejor_vendedor");
        }

        if (cedulaPeorVendedor.equals(cedulaMejorVendedor)){
            cedulaPeorVendedor = "No tiene";
            totalPeorVendedor = 0;
        }


        EstadisticasDepartamentales estadisticasDepartamentales = new EstadisticasDepartamentales(totalVentasDepartamento,nombreMejorCiudad,totalVentasMejorCiudad,cedulaMejorVendedor,totalVentasMejorVendedor,cedulaPeorVendedor,totalPeorVendedor);

        return estadisticasDepartamentales;
    }
}


