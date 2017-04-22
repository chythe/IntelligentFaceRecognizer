package pl.polsl.recognizer.controller;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import pl.polsl.recognizer.view.Dialog;

/**
 * Created by chythe on 2017-04-22.
 */
public class WindowController implements Initializable {


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
     * Action on clicked button: "Help".
     */
    @FXML
    public void helpButtonOnAction() {

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
}
