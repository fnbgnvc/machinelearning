package assignment1_data.part3;

import java.io.*;
import java.util.*;

public class perceptron {
    ArrayList<Instance> instances;
    double[] weights;
    double learningRate = 0.8;

    public static void main(String[] args) {
        // args: name of data file
        try {
            if (args.length != 1) {
                throw new Error("wrong number of args");
            }
            String path = System.getProperty("user.dir") + "\\part3\\" + args[0];

            new perceptron(path);
        } catch (Error e) { 
            e.printStackTrace();
            System.exit(0);
        }

    }

    // until the perceptron is always right (or fails to converge within 100 iter.)
    /*
     * present an example(+ve or -ve)
     * if perceptron is correct, do nothing
     * else if -ve and wrong:
     * subtract feature vector from weight vector
     * else if +ve and wrong:
     * add feature vector to weight vector
     * 
     * then use perceptron to classify all examples and print accuracy
     */
    perceptron(String path) {
        instances = readInstances(path);
        weights = new double[instances.get(0).vars.length];
        for(int i = 0; i<weights.length;i++){
            weights[i]=1;
        }
        runPerceptron();

    }
    public void runPerceptron(){
        trainPerceptron(instances);
        int repeats=0;
        double lastTest = 0;
        while(lastTest<1 && repeats<100){
            lastTest = testPerceptron(instances);
            trainPerceptron(instances);
            repeats++;
            //System.out.println(testPerceptron(instances) + " " + repeats);
        }
        int incorrect = Math.toIntExact(Math.round(instances.size() - instances.size()*testPerceptron(instances)));
        System.out.println(testPerceptron(instances) + " accuracy | " + repeats + " repeats | incorrect: " + incorrect + "/" + instances.size());
    }

    public void trainPerceptron(ArrayList<Instance> insts) {
        // sum of weight(i)*feature(i)>0, then true/good
        // (feature(0)=1)
        //true-true = 1-1=0
        //false-false = 0-0=0
        //true-false = 1
        //false-true = -1
        //wi+= learningRate(class-predictedclass)xi where xi is feature[i]
        for (Instance i : insts) {
            double sum = 0;
            sum+= weights[0];
            for (int j = 1; j < weights.length; j++) {
                sum += weights[j]*i.getVar(j-1);
            }
            if ((sum > 0 && i.good) || (sum < 0 && !i.good)) {
                // do nothing
            } 
            //if actual is false and predicted is true
            else if (sum > 0 && !i.good) {
                weights[0]--;
                for(int k = 1; k<weights.length;k++){
                    weights[k]-=(learningRate*i.getVar(k-1));
                }
                // add feature vector to weight vector
            } 
            //if actual is true and predicted is false: 1-0 = 1
            else if (sum <= 0 && i.good) {
                weights[0]++;
                for(int k=1; k< weights.length;k++){
                    weights[k]+=(learningRate*i.getVar(k-1));
                }
                // subtract feature vector from weight vector
            }
        }
    }

    public double testPerceptron(ArrayList<Instance> insts){
        double accuracy =0;
        for(Instance i:insts){
            double sum = 0;
            sum+= weights[0];
            for (int j = 1; j < weights.length; j++) {
                sum += weights[j]*i.getVar(j-1);
            }
            if ((sum > 0 && i.good) || (sum < 0 && !i.good)) {
                accuracy++;
            }
        }
        return accuracy/(double)insts.size();
    }

    public ArrayList<Instance> readInstances(String path) {
        File f = new File(path);
        try {
            Scanner s = new Scanner(f);
            s.nextLine(); // skipping labels
            ArrayList<Instance> insts = new ArrayList<>();
            while (s.hasNext()) {
                Instance n = new Instance();
                double[] atts = new double[34];
                for (int i = 0; i < atts.length; i++) {
                    atts[i] = s.nextDouble();
                }
                n.setVars(atts);
                String sh = s.next(); // System.out.println(s.next());
                if (sh.equals("g")) {
                    n.setClass(true);
                } else if (sh.equals("b")) {
                    n.setClass(false);
                } else {
                    throw new Error("messed up scanner");
                }
                insts.add(n);
            }

            s.close();
            return insts;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // read data: features 1-34 and string class
        return null;
    }
}
