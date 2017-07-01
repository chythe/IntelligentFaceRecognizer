package pl.polsl.recognizer;

import java.io.File;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.Document;
import pl.polsl.recognizer.controller.WindowController;
import pl.polsl.recognizer.model.Face;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                new File("src/main/resources/pl/polsl/recognizer/view/Window.fxml").toURI().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Intelligent Face Recognizer");
        stage.setOnCloseRequest(WindowController.confirmDialogCloseRequest());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
