package pl.polsl.recognizer;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.recognizer.controller.WindowController;

public class Main extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set
     * @throws Exception if load method from FXMLLoader class
     * have problem with fxml file
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                new File("src/main/java/pl/polsl/recognizer/view/Window.fxml")
                        .toURI().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Intelligent Face Recognizer");
        stage.setOnCloseRequest(WindowController.confirmDialogCloseRequest());
        stage.show();
    }

    /**
     * Main method of application.
     * @param args command line parameters
     */
    public static void main(String[] args) {
        launch(args);
    }
}
