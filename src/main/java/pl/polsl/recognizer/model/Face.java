package pl.polsl.recognizer.model;

import java.util.List;

/**
 * Created by chythe on 2017-04-22.
 */
public class Face {

    public static final short NUM_OF_FACE_PARAMS = 8;

    private double distanceEyes;

    private double distanceLeftEyeMouth;

    private double distanceRightEyeMouth;

    private double distanceLeftEyeNose;

    private double distanceRightEyeNose;

    private double distanceMouthNose;

    private double distanceEyesNose;

    private double widthNose;

    public Face(double distanceEyes,
                double distanceLeftEyeMouth,
                double distanceRightEyeMouth,
                double distanceLeftEyeNose,
                double distanceRightEyeNose,
                double distanceMouthNose,
                double distanceEyesNose,
                double widthNose) {
        this.distanceEyes = distanceEyes;
        this.distanceLeftEyeMouth = distanceLeftEyeMouth;
        this.distanceRightEyeMouth = distanceRightEyeMouth;
        this.distanceLeftEyeNose = distanceLeftEyeNose;
        this.distanceRightEyeNose = distanceRightEyeNose;
        this.distanceMouthNose = distanceMouthNose;
        this.distanceEyesNose = distanceEyesNose;
        this.widthNose = widthNose;
    }

    double[] getAll() {
        double[] allParameters = {
                distanceEyes,
                distanceLeftEyeMouth,
                distanceRightEyeMouth,
                distanceLeftEyeNose,
                distanceRightEyeNose,
                distanceMouthNose,
                distanceEyesNose,
                widthNose
        };
        return allParameters;
    }

    public double getDistanceEyes() {
        return distanceEyes;
    }

    public void setDistanceEyes(double distanceEyes) {
        this.distanceEyes = distanceEyes;
    }

    public double getDistanceLeftEyeMouth() {
        return distanceLeftEyeMouth;
    }

    public void setDistanceLeftEyeMouth(double distanceLeftEyeMouth) {
        this.distanceLeftEyeMouth = distanceLeftEyeMouth;
    }

    public double getDistanceRightEyeMouth() {
        return distanceRightEyeMouth;
    }

    public void setDistanceRightEyeMouth(double distanceRightEyeMouth) {
        this.distanceRightEyeMouth = distanceRightEyeMouth;
    }

    public double getDistanceLeftEyeNose() {
        return distanceLeftEyeNose;
    }

    public void setDistanceLeftEyeNose(double distanceLeftEyeNose) {
        this.distanceLeftEyeNose = distanceLeftEyeNose;
    }

    public double getDistanceRightEyeNose() {
        return distanceRightEyeNose;
    }

    public void setDistanceRightEyeNose(double distanceRightEyeNose) {
        this.distanceRightEyeNose = distanceRightEyeNose;
    }

    public double getDistanceMouthNose() {
        return distanceMouthNose;
    }

    public void setDistanceMouthNose(double distanceMouthNose) {
        this.distanceMouthNose = distanceMouthNose;
    }

    public double getDistanceEyesNose() {
        return distanceEyesNose;
    }

    public void setDistanceEyesNose(double distanceEyesNose) {
        this.distanceEyesNose = distanceEyesNose;
    }

    public double getWidthNose() {
        return widthNose;
    }

    public void setWidthNose(double widthNose) {
        this.widthNose = widthNose;
    }

    @Override
    public String toString() {
        return "Face{" +
                "distanceEyes=" + distanceEyes +
                ", distanceLeftEyeMouth=" + distanceLeftEyeMouth +
                ", distanceRightEyeMouth=" + distanceRightEyeMouth +
                ", distanceLeftEyeNose=" + distanceLeftEyeNose +
                ", distanceRightEyeNose=" + distanceRightEyeNose +
                ", distanceMouthNose=" + distanceMouthNose +
                ", distanceEyesNose=" + distanceEyesNose +
                ", widthNose=" + widthNose +
                '}';
    }
}
