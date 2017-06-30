package pl.polsl.recognizer;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.recognizer.controller.WindowController;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                new File("src/main/resources/fxml/pl/polsl/recognizer/view/Window.fxml").toURI().toURL());
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
