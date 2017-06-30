package pl.polsl.recognizer.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.openimaj.image.FImage;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.math.geometry.shape.Rectangle;
import pl.polsl.recognizer.model.FaceParameterizer;
import pl.polsl.recognizer.model.RecognizerNeuralNetwork;
import pl.polsl.recognizer.view.Dialog;

/**
 * Created by chythe on 2017-04-22.
 */
public class WindowController implements Initializable {

    @FXML
    private Button addTabCameraButton;

    @FXML
    private Button recognizeTabCameraButton;

    @FXML
    private Button addFaceButton;

    @FXML
    private Button recognizeFaceButton;

    @FXML
    private Button helpButton;

    @FXML
    ComboBox<WebCamInfo> addTabCameraComboBox;

    @FXML
    ComboBox<WebCamInfo> recognizeTabCameraComboBox;

    @FXML
    ImageView addFaceImageView;

    @FXML
    ImageView recognizeFaceImageView;

    private BufferedImage bufferedImage;

    private Webcam webCam = null;

    private boolean stopCamera = false;

    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

    private DetectedFace detectedface = null;

    public WindowController() {

    }

    private class WebCamInfo {

        private String webCamName;

        private int webCamIndex;

        public String getWebCamName() {
            return webCamName;
        }

        public void setWebCamName(String webCamName) {
            this.webCamName = webCamName;
        }

        public int getWebCamIndex() {
            return webCamIndex;
        }

        public void setWebCamIndex(int webCamIndex) {
            this.webCamIndex = webCamIndex;
        }

        @Override
        public String toString() {
            return webCamName;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
        int webCamCounter = 0;
        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamInfo webCamInfo = new WebCamInfo();
            webCamInfo.setWebCamIndex(webCamCounter);
            webCamInfo.setWebCamName(webcam.getName());
            options.add(webCamInfo);
            webCamCounter++;
        }
        addTabCameraComboBox.setItems(options);
        addTabCameraComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

            @Override
            public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
                if (arg2 != null) {
                    System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
                    initializeWebCam(arg2.getWebCamIndex());
                }
            }
        });
    }

    protected void initializeWebCam(final int webCamIndex) {

        Task<Void> webCamIntilizer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (webCam == null) {
                    webCam = Webcam.getWebcams().get(webCamIndex);
                    webCam.open();
                } else {
                    webCam.close();
                    webCam = Webcam.getWebcams().get(webCamIndex);
                    webCam.open();
                }
                startWebCamStream();
                return null;
            }

        };
        addFaceButton.setDisable(true);

        new Thread(webCamIntilizer).start();
    }

    protected void startWebCamStream() {
        stopCamera = false;
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (!stopCamera) {
                    try {
                        if ((bufferedImage = webCam.getImage()) != null) {

                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    final Image image = SwingFXUtils
                                            .toFXImage(bufferedImage, null);
//                                    final FImage fImage = ImageUtilities
                                    imageProperty.set(image);

                                }
                            });

                            bufferedImage.flush();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        addFaceImageView.imageProperty().bind(imageProperty);

    }

    @FXML
    public void addTabCameraOnAction() {
        if (stopCamera) {
            stopCamera = false;
            startWebCamStream();
        } else {
            stopCamera = true;
        }
    }

    @FXML
    public void addFaceOnAcrion() {

    }

    @FXML
    public void recognizeTabCameraOnAction() {
//        if (stopCamera) {
//            stopCamera = false;
//            startWebCamStream();
//        } else {
//            stopCamera = true;
//        }
    }

    @FXML
    public void recognizeFaceOnAction() {

    }

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

//    @FXML
//    public void browseButtonOnAction() {
//        FileChooser fileChooser = new FileChooser();
//        FileChooser.ExtensionFilter extFilter =
//                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//        fileChooser.getExtensionFilters().add(extFilter);
//        File file = fileChooser.showOpenDialog(new Stage());
//        if (file != null) {
//            inputPicturePathTextField.setText(file.getAbsolutePath());
//        }
//    }

    @FXML
    public void recognizeFaceButtonOnAction() {
//        RecognizerNeuralNetwork rocognizer = new RecognizerNeuralNetwork();
//        rocognizer.learnNeuralNetwork(inputPicturePathTextField.getText());
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        File selectedDirectory = directoryChooser.showDialog(new Stage());
//        if (selectedDirectory != null) {
//            FaceParameterizer faceParameterizer = new FaceParameterizer();
//            faceParameterizer.detectFace(inputPicturePathTextField.getText(), selectedDirectory.getAbsolutePath() + "\\output.png");
//        }
    }

    @FXML
    public void helpButtonOnAction() {

    }
}
