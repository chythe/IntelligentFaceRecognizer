package pl.polsl.recognizer.model;

public class Face {

    public static final short NUM_OF_FACE_PARAMS = 8;

    private String name;

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

    public Face(String name,
                double distanceEyes,
                double distanceLeftEyeMouth,
                double distanceRightEyeMouth,
                double distanceLeftEyeNose,
                double distanceRightEyeNose,
                double distanceMouthNose,
                double distanceEyesNose,
                double widthNose) {
        this.name = name;
        this.distanceEyes = distanceEyes;
        this.distanceLeftEyeMouth = distanceLeftEyeMouth;
        this.distanceRightEyeMouth = distanceRightEyeMouth;
        this.distanceLeftEyeNose = distanceLeftEyeNose;
        this.distanceRightEyeNose = distanceRightEyeNose;
        this.distanceMouthNose = distanceMouthNose;
        this.distanceEyesNose = distanceEyesNose;
        this.widthNose = widthNose;
    }

    public double[] getAll() {
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

    public double getMinParam() {
        double[] params = getAll();
        double min = params[0];
        for (int i = 1; i < params.length; i++) {
            if (params[i] < min)
                min = params[i];
        }
        return min;
    }

    public double getMaxParam() {
        double[] params = getAll();
        double max = params[0];
        for (int i = 1; i < params.length; i++) {
            if (params[i] > max)
                max = params[i];
        }
        return max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
