package pl.polsl.recognizer;

import org.opencv.core.Core;

public class Main {

    static {
        nu.pattern.OpenCV.loadLocally();
    }

    public static void main(String[] args) {
        System.out.println("Hello OpenCV " + Core.VERSION);
    }
}
