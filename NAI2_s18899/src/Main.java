import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public Main(String trainSet, String testSet) {
        Scanner scanner = new Scanner(System.in);
        String decision = "";
        List<Vector<Double>> trainingSet = new ArrayList<>();
        List<Vector<Double>> testingSet = new ArrayList<>();
        Perceptron perceptron = new Perceptron(trainingSet, testingSet, 0.5, 5);
        while (!decision.equals("finish")) {
            System.out.println("Enter ====> 'rf' to analyse from the test file " +
                    "| 'sv' to analyse a single vector | 'finish' to stop the program ");
            decision = scanner.nextLine().trim();
            if (decision.equals("rf")) {

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(trainSet)); // going through the test file
                    String line;
                    while ((line = reader.readLine()) != null) {
                        //   System.out.println("Input: " + line);

                        Vector<Double> trainingVector = parseVector(line);
                        trainingSet.add(trainingVector);
                    }
                    reader = new BufferedReader(new FileReader(testSet)); // going through the test file
                    while ((line = reader.readLine()) != null) {
                        //   System.out.println("Input: " + line);
                        Vector<Double> testVector = parseVector(line);
                        testingSet.add(testVector);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Training set of vectors: " + trainingSet);
                System.out.println("Testing set of vectors: " + testingSet);
                perceptron.trainPerceptron();
                perceptron.testFromFile();

            } else if (decision.equals("sv")) {
                String line;
                System.out.println("Enter 4 double parameters of the flower and it's name separated with comma");
                line = scanner.nextLine().trim();
                System.out.println("Input: " + line);
                Vector<Double> vector = parseVector(line);
                perceptron.trainPerceptron();
                perceptron.classify(vector);
            }
        }
    }

    public Vector<Double> parseVector(String line) {
        Vector<Double> resVector = new Vector<>();
        String[] data = line.split(",");
        for (int i = 0; i < data.length - 1; i++) {
            resVector.add(Double.parseDouble(data[i]));
        }
        if (data[data.length - 1].equals("Iris-setosa")) {
            resVector.add(1.0);
        } else resVector.add(0.0);                      //classifying if its setosa then output 1, otherwise 0
        return resVector;
    }

    public static void main(String[] args) {
        new Main(args[0], args[1]);
    }
}
