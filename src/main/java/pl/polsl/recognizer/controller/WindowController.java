package pl.polsl.recognizer.controller;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.polsl.recognizer.model.FaceParameterizer;
import pl.polsl.recognizer.view.Dialog;

/**
 * Created by chythe on 2017-04-22.
 */
public class WindowController implements Initializable {

    /**
     * Handle to a TextFiled object, representing a text filed for entering input picture path.
     */
    @FXML
    private TextField inputPicturePathTextField;

    /**
     * Handle to a Button object, representing a browse option.
     */
    @FXML
    private Button browseButton;

    /**
     * Handle to a Button object, representing a detect face option.
     */
    @FXML
    private Button detectFaceButton;

    /**
     * Handle to a Button object, representing a help option.
     */
    @FXML
    private Button helpButton;

    /**
     * No arguments constructor.
     */
    public WindowController() {

    }

    /**
     * The initialization method controller after its root
     * element has been completely processed.
     * @param location the location used to resolve relative paths for the
     * root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if
     * the root object was not localized
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * The method returns event handler to display confirm dialog window.
     * @return event handler to display confirm dialog window
     */
    public static EventHandler<WindowEvent> confirmDialogCloseRequest() {
        return new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent windowEvent) {
                Dialog dialogs = new Dialog();
                Optional<ButtonType> result =
                        dialogs.getExitConfirmDialogAlert().showAndWait();
                if (result.get() == ButtonType.OK){
                    Platform.exit();
                    System.exit(0);
                } else
                    windowEvent.consume();
            }
        };
    }


    /**
     * Action on selected menu option: "Exit".
     */
    @FXML
    public void exitMenuItemOnAction() {
        Dialog dialogs = new Dialog();
        Optional<ButtonType> result =
                dialogs.getExitConfirmDialogAlert().showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * Action on selected menu option: "Browse".
     */
    @FXML
    public void browseButtonOnAction() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            inputPicturePathTextField.setText(file.getAbsolutePath());
        }
    }

    /**
     * Action on clicked button: "Detect Face".
     */
    @FXML
    public void detectFaceButtonOnAction() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            FaceParameterizer faceParameterizer = new FaceParameterizer();
            faceParameterizer.detectFace(inputPicturePathTextField.getText(), selectedDirectory.getAbsolutePath() + "\\output.png");
        }
    }

    /**
     * Action on clicked button: "Help".
     */
    @FXML
    public void helpButtonOnAction() {

    }
}
