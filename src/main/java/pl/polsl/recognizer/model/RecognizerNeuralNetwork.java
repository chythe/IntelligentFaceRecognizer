package pl.polsl.recognizer.model;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.util.TransferFunctionType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by chythe on 2017-04-22.
 */
public class RecognizerNeuralNetwork {

    private MultiLayerPerceptron neuralNetwork;
    private DataSet trainingSet;

    public RecognizerNeuralNetwork() {
    }

    public void learnNeuralNetwork(String inputFilePath) {
        int columnCount = 0;
        int rowCount = 0;
        Scanner scan;
        File file = new File(inputFilePath);
        List<double[]> facesParameters = new ArrayList();
        try {
            scan = new Scanner(file);
            while(scan.hasNextDouble()) {
                if (0 == columnCount)
                    facesParameters.add(new double[8]);
                facesParameters.get(rowCount)[columnCount] = scan.nextDouble();
                if (++columnCount >= 8) {
                    columnCount = 0;
                    rowCount++;
                }
                if (++columnCount >= 16) {
                    columnCount = 0;
                    rowCount++;
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 8, 3, 15);
        trainingSet = new DataSet(8, 15);
        Iterator<double[]> trainingSetIterator = trainingSet.iterator();
//        Iterator<short[]> resultSetIterator = resultSet.iterator();
        while (trainingSetIterator.hasNext() /* && resultSetIterator.hasNext()*/) {
            trainingSet.addRow(trainingSetIterator.next());
        }
//        facesParameters.forEach(t -> {
//            trainingSet.addRow(t);
//        });
        neuralNetwork.learn(trainingSet);
    }
}
