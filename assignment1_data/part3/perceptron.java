package assignment1_data.part3;

import java.io.*;
import java.util.*;

public class perceptron {
    ArrayList<Instance> instances;
    double[] weights;

    public static void main(String[] args) {
        // args: name of data file
        try {
            if (args.length != 1) {
                throw new Error("wrong number of args");
            }
            String path = System.getProperty("user.dir") + "\\assignment1_data\\part3\\" + args[0];

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
        trainPerceptron(instances);

    }

    public void trainPerceptron(ArrayList<Instance> insts) {
        // sum of weight(i)*feature(i)>0, then true/good
        // (feature(0)=1)
        for (Instance i : insts) {
            double sum = 0;
            for (int j = 0; j < i.vars.length; j++) {
                sum += weights[j] + i.getVar(j);
            }
            if (sum > 0 && i.good || sum < 0 && !i.good) {
                // do nothing
            } else if (sum > 0 && !i.good) {
                // add feature vector to weight vector
            } else if (sum < 0 && i.good) {
                // subtract feature vector from weight vector
            }
        }
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
