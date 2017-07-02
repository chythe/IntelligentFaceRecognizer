package pl.polsl.recognizer.model;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;
import pl.polsl.recognizer.exception.NoFaceException;
import java.util.*;

public class RecognizerNeuralNetwork {

    private NeuralNetwork neuralNetwork;

    private DataSet trainingSet;

    private static RecognizerNeuralNetwork instance = new RecognizerNeuralNetwork();

    public static RecognizerNeuralNetwork getInstance() { return instance; }

    protected RecognizerNeuralNetwork() {}

    public void addFace(List<Face> faces) throws NoFaceException {
        long startTime = System.nanoTime();
        if (faces.isEmpty()) {
            throw new NoFaceException("No face in list");
        } else if (faces.size() < 3) {
            neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,
                    Face.NUM_OF_FACE_PARAMS, faces.size());
        } else {
            int middleNeuronsInLayers = Math.round(faces.size() / 3);
            if (middleNeuronsInLayers < 2)
                middleNeuronsInLayers = 2;
            neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,
                    Face.NUM_OF_FACE_PARAMS, middleNeuronsInLayers, faces.size());
        }
        trainingSet = new DataSet(Face.NUM_OF_FACE_PARAMS, faces.size());
//        double min = getMinParam(faces);
//        double max = getMaxParam(faces);
        for (int i = 0; i < faces.size(); i++) {
            trainingSet.addRow(new DataSetRow(
                    normalize(faces.get(i).getAll(), faces.get(i).getMinParam(), faces.get(i).getMaxParam()),
                    generateResult(i, faces.size())));
        }
        System.out.println("Start neural network learning");
        neuralNetwork.learn(trainingSet);
        neuralNetwork.save("intelligent_face_recognizer.nnet");
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("Add face time: " + duration / 1000000 / 1000 + " seconds (" + duration / 1000000 + " milliseconds).");
    }

    public int recognizeFace(Face face) throws NoFaceException {
        long startTime = System.nanoTime();
        if ((neuralNetwork = NeuralNetwork.load("intelligent_face_recognizer.nnet")) == null)
            throw new NoFaceException("No neural network file");
        neuralNetwork.setInput(normalize(face.getAll(), face.getMinParam(), face.getMaxParam()));
        neuralNetwork.calculate();
        double[] results = neuralNetwork.getOutput();
        System.out.println("Results: " + Arrays.toString(results));
        int result = -1;
        for (int i = 0; i < results.length; i++) {
            if (results[i] > 0.5) {
                if (-1 == result || results[i] > results[result]) {
                    result = i;
                }
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("Recognize face time: " + duration / 1000000 / 1000 + " seconds (" + duration / 1000000 + " milliseconds).");
        if (result != -1)
            return result;
        else
            throw new NoFaceException("No face in database");
    }

    private double getMinParam(List<Face> faces) {
        double min = faces.get(0).getMinParam();
        for (int i = 1; i < faces.size(); i++) {
            double tmp = faces.get(i).getMinParam();
            if (tmp < min)
                min = tmp;
        }
        return min;
    }

    private double getMaxParam(List<Face> faces) {
        double max = faces.get(0).getMaxParam();
        for (int i = 1; i < faces.size(); i++) {
            double tmp = faces.get(i).getMaxParam();
            if (tmp > max)
                max = tmp;
        }
        return max;
    }

    private double[] normalize(double[] numbers, double min, double max) {
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = (numbers[i] - min) / (max - min);
        return numbers;
    }

    public double[] generateResult(int index, int size) {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            if (i == index) {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }
        return result;
    }
}
