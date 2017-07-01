package pl.polsl.recognizer.controller;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;
import pl.polsl.recognizer.exception.NoFaceException;
import pl.polsl.recognizer.model.FaceParameterizer;
import pl.polsl.recognizer.model.RecognizerNeuralNetwork;

public class WindowController implements Initializable {

    @FXML
    private Button cameraButton;

    @FXML
    private Button addFaceButton;

    @FXML
    private Button recognizeFaceButton;

    @FXML
    ComboBox<WebCamInfo> cameraComboBox;

    @FXML
    ImageView imageView;

    @FXML
    BorderPane borderPane;

    private BufferedImage bufferedImage;

    private Webcam webCam = null;

    private boolean stopCamera = false;

    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

    public WindowController() {}

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
        cameraButton.setDisable(true);
        addFaceButton.setDisable(true);
        recognizeFaceButton.setDisable(true);
        ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
        int webCamCounter = 0;
        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamInfo webCamInfo = new WebCamInfo();
            webCamInfo.setWebCamIndex(webCamCounter);
            webCamInfo.setWebCamName(webcam.getName());
            options.add(webCamInfo);
            webCamCounter++;
        }
        cameraComboBox.setItems(options);
        cameraComboBox.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                initializeWebCam(arg2.getWebCamIndex());
            }
        });
        Platform.runLater(() -> setImageViewSize());
    }

    protected void setImageViewSize() {
        double height = borderPane.getHeight();
        double width = borderPane.getWidth();
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.prefHeight(height);
        imageView.prefWidth(width);
        imageView.setPreserveRatio(true);
    }

    protected void initializeWebCam(final int webCamIndex) {
        Task<Void> webCamInitializerTask = new Task<Void>() {

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
                cameraButton.setDisable(false);
                return null;
            }

        };
        new Thread(webCamInitializerTask).start();
    }

    protected void startWebCamStream() {
        stopCamera = false;
        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (!stopCamera) {
                    try {
                        if ((bufferedImage = webCam.getImage()) != null) {
                            Platform.runLater(() -> {
                                final Image fxImage = SwingFXUtils
                                        .toFXImage(bufferedImage, null);
                                imageProperty.set(fxImage);
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
        Thread thread = new Thread(webCamTask);
        thread.setDaemon(true);
        thread.start();
        imageView.imageProperty().bind(imageProperty);
    }

    @FXML
    public void cameraOnAction() {
        if (stopCamera) {
            stopCamera = false;
            addFaceButton.setDisable(true);
            recognizeFaceButton.setDisable(true);
            cameraButton.setText("Stop Camera");
            startWebCamStream();
        } else {
            stopCamera = true;
            addFaceButton.setDisable(false);
            recognizeFaceButton.setDisable(false);
            cameraButton.setText("Start Camera");
        }
    }

    @FXML
    public void addFaceOnAction() {
        try {
            RecognizerNeuralNetwork.getInstance().addFace(
                    FaceParameterizer.detectFace(bufferedImage));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Face added to the database");
            alert.showAndWait();
        } catch (NoFaceException e) {
            showNoFaceWarning(e.getMessage());
        }
    }

    @FXML
    public void recognizeFaceOnAction() {
        // TODO
        try {
            String name = RecognizerNeuralNetwork.getInstance().recognizeFace(
                    FaceParameterizer.detectFace(bufferedImage));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Face was recognized as:");
            alert.setContentText(name);
            alert.showAndWait();
        } catch (NoFaceException e) {
            showNoFaceWarning(e.getMessage());
        }
//        RecognizerNeuralNetwork rocognizer = new RecognizerNeuralNetwork();
//        rocognizer.learnNeuralNetwork(inputPicturePathTextField.getText());
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        File selectedDirectory = directoryChooser.showDialog(new Stage());
//        if (selectedDirectory != null) {
//            FaceParameterizer faceParameterizer = new FaceParameterizer();
//            faceParameterizer.detectFace(inputPicturePathTextField.getText(), selectedDirectory.getAbsolutePath() + "\\output.png");
//        }
    }

    private void showNoFaceWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static EventHandler<WindowEvent> confirmDialogCloseRequest() {
        return windowEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Confirm");
            alert.setContentText("Are you sure you want to exit?");
            if (alert.showAndWait().get() == ButtonType.OK){
                Platform.exit();
                System.exit(0);
            } else
                windowEvent.consume();
        };
    }
}
