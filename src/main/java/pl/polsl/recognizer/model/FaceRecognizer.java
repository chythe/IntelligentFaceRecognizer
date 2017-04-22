package pl.polsl.recognizer.model;

import org.opencv.core.Core;
import nu.pattern.OpenCV;

/**
 * Created by chythe on 2017-04-22.
 */
public class FaceRecognizer {

    static {
        OpenCV.loadLocally();
    }

    public static String GetOpenCVVersion() {
        return Core.VERSION;
    }
}
