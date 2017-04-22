package pl.polsl.recognizer.view;

import javafx.scene.control.Alert;

/**
 * Created by chythe on 2017-04-22.
 */
public class Dialog {
    /**
     * Dialog window represents information about the end of doing something.
     */
    private final Alert doneInfoDialogAlert;

    /**
     * Dialog window represents help information.
     */
    private final Alert helpInfoDialogAlert;

    /**
     * Dialog window represents the question of the exit from the program.
     */
    private final Alert exitConfirmDialogAlert;

    /**
     * No arguments constructor.
     */
    public Dialog() {
        doneInfoDialogAlert = new Alert(Alert.AlertType.INFORMATION);
        helpInfoDialogAlert = new Alert(Alert.AlertType.INFORMATION);
        exitConfirmDialogAlert = new Alert(Alert.AlertType.CONFIRMATION);
        doneInfoDialogAlertInitialize();
        exitConfirmDialogAlertInitialize();
        helpInfoDialogAlertInitialize();
    }

    /**
     * The initialization method doneInfoDialogAlert.
     */
    private void doneInfoDialogAlertInitialize() {
        doneInfoDialogAlert.setTitle("Information");
        doneInfoDialogAlert.setHeaderText("Done.");
    }

    /**
     * The initialization method exitConfirmDialogAlert.
     */
    private void exitConfirmDialogAlertInitialize() {
        exitConfirmDialogAlert.setTitle("Exit");
        exitConfirmDialogAlert.setHeaderText("Confirm");
        exitConfirmDialogAlert.setContentText("Are you sure you want to exit?");
    }

    /**
     * The initialization method helpInfoDialogAlert.
     */
    private void helpInfoDialogAlertInitialize() {
        helpInfoDialogAlert.setTitle("Information");
        helpInfoDialogAlert.setHeaderText("Help");
        helpInfoDialogAlert.setContentText(
                ""
        );
    }

    /**
     * Gets done information dialog window.
     * @return done information dialog window
     */
    public Alert getDoneInfoDialogAlert() {
        return doneInfoDialogAlert;
    }

    /**
     * Gets help information dialog window.
     * @return help information dialog window
     */
    public Alert getHelpInfoDialogAlert() {
        return helpInfoDialogAlert;
    }

    /**
     * Gets exit confirmation dialog window.
     * @return exit confirmation dialog window
     */
    public Alert getExitConfirmDialogAlert() {
        return exitConfirmDialogAlert;
    }

}
