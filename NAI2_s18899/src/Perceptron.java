import java.util.List;
import java.util.Vector;

class Perceptron {
    private double learningRate;
    private int numOfIterations;
    private List<Vector<Double>> trainingSet;
    private List<Vector<Double>> testingSet;
    private double[] weightVector = new double[4];
    private double bias = 3;
    private static final String one = "Iris-setosa";
    private static final String zero = "Iris-versicolor";
    private int correctPredictions;

    Perceptron(List<Vector<Double>> trainingSet, List<Vector<Double>> testingSet, double learningRate, int numOfIterations) {
        this.trainingSet = trainingSet;
        this.testingSet = testingSet;
        this.learningRate = learningRate;
        this.numOfIterations = numOfIterations;
        for (int i = 0; i < weightVector.length; i++) {
            weightVector[i] = Math.random() * 10;
        }
    }

    void testFromFile() {
        correctPredictions = 0;
        for (Vector<Double> vector : testingSet) {
            classify(vector);
        }
        System.out.println("Accuracy of the test data " + ((double) correctPredictions / testingSet.size()) * 100 + "%");
    }

    void classify(Vector<Double> vector) {
        double expectedOut = vector.get(vector.size() - 1);
        double calculateOutput = calculateOutput(vector); // output y
        if (expectedOut == calculateOutput && calculateOutput == 1.0) {
            correctPredictions++;
            System.out.println("Predicted class " + one + " Calculated Class " + one);
        } else if (expectedOut == calculateOutput && calculateOutput == 0.0) {
            correctPredictions++;
            System.out.println("Predicted class " + zero + " Calculated Class " + zero);
        } else if (expectedOut != calculateOutput && calculateOutput == 0.0) {
            System.out.println("Predicted class " + one + " Calculated Class " + zero);
        } else System.out.println("Predicted class: " + zero + " Calculated Class " + one);
    }

    void trainPerceptron() {
        System.out.println("Started training...");
        for (int i = 1; i < numOfIterations + 1; i++) {
            System.out.println("Starting " + i + " iteration");
            deltaRule();
        }
    }

    private void deltaRule() {
        int numOfErrors = 0;
        double expectedOut = 0;
        double calculateOutput = 0;
        bias = 3;
        // step2 calculating output y and updateWights,bias
        for (Vector<Double> vector : trainingSet) {
            expectedOut = vector.get(vector.size() - 1);
            calculateOutput = calculateOutput(vector); // output y
            updateWeightsAndBias(expectedOut, calculateOutput, vector);

            if (expectedOut - calculateOutput != 0) {
                numOfErrors++;
            }
        }
        //step3 iterationError
        double iterationError = calculateIterationError(numOfErrors, expectedOut, calculateOutput);
        //step 4 doing recursive to 2nd step when error is not less than threshold
        //System.out.println(iterationError + " " + bias);
        System.out.println("After the iteration, number of errors: " + numOfErrors);
        if (iterationError >= bias) {
            deltaRule();
        }
    }

    private double  calculateOutput(Vector<Double> vector) {
        double res = 0;
        for (int i = 0; i < vector.size() - 1; i++) {
            res += vector.get(i) * weightVector[i];
        }
        if (res - bias > bias) {
            return 1.0;
        } else return 0.0;
    }

    private void updateWeightsAndBias(double expectedOut, double calculateOutput, Vector<Double> vector) {
        double n = learningRate * (expectedOut - calculateOutput);
        // System.out.println("OLD weight vector: " + Arrays.toString(weightVector) + " old bias " + bias);
        for (int i = 0; i < vector.size() - 1; i++) {
            weightVector[i] += n * vector.get(i);
        }
        bias -= n;
        //System.out.println("NEW weight vector: " + Arrays.toString(weightVector) + " new bias " + bias);
    }

    private double calculateIterationError(int numOfIterations, double expectedOut, double calculateOutput) {
        double e = 0;
        for (int i = 0; i < numOfIterations; i++) {
            e += (Math.abs(expectedOut - calculateOutput));
        }
        return e;
    }
}
