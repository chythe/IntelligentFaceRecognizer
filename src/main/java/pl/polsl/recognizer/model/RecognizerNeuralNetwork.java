package pl.polsl.recognizer.model;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.Perceptron;

import java.io.FileNotFoundException;

/**
 * Created by chythe on 2017-04-22.
 */
public class RecognizerNeuralNetwork {

    private NeuralNetwork neuralNetwork;

    public RecognizerNeuralNetwork() {
        this.neuralNetwork = NeuralNetwork.load("FaceRecognizerPerceptron.nnet");
        if (null == neuralNetwork)
            neuralNetwork = new Perceptron(2, 1);
    }
}
