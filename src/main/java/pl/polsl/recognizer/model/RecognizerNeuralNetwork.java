package pl.polsl.recognizer.model;

import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;
import pl.polsl.recognizer.exception.NoFaceException;

import java.io.*;
import java.util.*;

public class RecognizerNeuralNetwork {

    private MultiLayerPerceptron neuralNetwork;

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
            neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,
                    Face.NUM_OF_FACE_PARAMS, Math.round(faces.size() / 3), faces.size());
        }
        trainingSet = new DataSet(Face.NUM_OF_FACE_PARAMS, faces.size());
        double min = getMinParam(faces);
        double max = getMaxParam(faces);
        for (int i = 0; i < faces.size(); i++) {
            trainingSet.addRow(new DataSetRow(normalize(faces.get(i).getAll(), min, max), generateResult(i, faces.size())));
        }
        neuralNetwork.learn(trainingSet);
        neuralNetwork.save("intelligent_face_recognizer.nnet");
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("Add face time: " + duration / 1000000 / 1000 + " seconds (" + duration / 1000000 + " milliseconds).");
    }

    public String recognizeFace(Face face) throws NoFaceException {
        long startTime = System.nanoTime();
        if (neuralNetwork.load("intelligent_face_recognizer.nnet") == null)
            throw new NoFaceException("No neural network file");
//        neuralNetwork.
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("Recognize face time: " + duration / 1000000 / 1000 + " seconds (" + duration / 1000000 + " milliseconds).");
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
            numbers[i] = (numbers[i] - min)/ (max - min);
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
