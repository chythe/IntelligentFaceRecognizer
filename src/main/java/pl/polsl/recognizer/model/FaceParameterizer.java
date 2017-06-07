package pl.polsl.recognizer.model;

import org.opencv.core.*;
import nu.pattern.OpenCV;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


/**
 * Created by chythe on 2017-04-22.
 */
public class FaceParameterizer {

    static {
        OpenCV.loadLocally();
    }

    public FaceParameterizer() {

    }

    public void detectFace(String inputFilePath, String outputFilePath) {
        System.out.println("\nRunning DetectFace");
        // Create a face detector from the cascade file in the resources
        // directory.
        CascadeClassifier faceDetector = new CascadeClassifier("src/main/resources/haarcascades/haarcascade_eye.xml");
        Mat image = Imgcodecs.imread(inputFilePath);
        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
        // Save the visualized detection.
        System.out.println(String.format("Writing %s", outputFilePath));
        Imgcodecs.imwrite(outputFilePath, image);
    }

    public static String GetOpenCVVersion() {
        return Core.VERSION;
    }
}
