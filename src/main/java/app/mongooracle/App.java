package app.mongooracle;

import app.mongooracle.bd.Mongo;
import app.mongooracle.bd.Oracle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {
    public final static String MONGO_CONECTION_URL = "mongodb://localhost:27017";
    public final static String MONGO_DATABASE_NAME = "db2";

    public static Mongo mongo;
    public static Oracle oracle;

    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        mongo = new Mongo(MONGO_CONECTION_URL,MONGO_DATABASE_NAME);
        oracle = new Oracle();
        scene = new Scene(loadFXML("/views/menu_principal"), 672, 438);
        stage.setScene(scene);
        stage.show();
    }
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml+".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch();
    }
}