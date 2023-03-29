package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class knn {
    ArrayList<Node> nodeList;
    ArrayList<Node> testList;

    public static void main(String[] args) throws Exception {

        try {
            if (args.length != 2) {
                throw new Error("wrong number of args");
            }
            String path1 = System.getProperty("user.dir") + "\\" + args[0];
            String path2 = System.getProperty("user.dir") + "\\" + args[1];

            new knn().runKnn(path1, path2, 3);
        } catch (Error e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * takes nearest neighbours from map of euclidean distances and calculates most common class 
     * @param training name of training file
     * @param test name of test file
     * @param k number of neighbours to consider
     */
    public void runKnn(String training, String test, int k) {
        if (k <= 0) {
            return;
        }
        ArrayList<Node> trainNodes = readFiles(training);
        ArrayList<Node> testNodes = readFiles(test);

        double[] ranges = rangeCalc(trainNodes);
        double accuracy = 0;
        // for each node in testNodes,
        for (int c = 0; c < testNodes.size(); c++) {
            Node i = testNodes.get(c);
            HashMap<Double, Node> h = new HashMap<>();
            for (Node j : trainNodes) {
                double dist = euclidianDist(i, j, ranges);
                h.put(dist, j);
            }
            List<Double> hlist = h.keySet().stream().collect(Collectors.toList());
            Collections.sort(hlist);
            List<Node> knearest = new ArrayList<Node>();
            int[] clasf = new int[k + 1];
            // counting how much of each classification
            for (int j = 0; j < k; j++) {
                knearest.add(h.get(hlist.get(j)));
                clasf[h.get(hlist.get(j)).classification]++;
            }

            int max = 0;
            int classif = 0;
            for (int l = 0; l < clasf.length; l++) {
                if (clasf[l] > max) {
                    max = clasf[l];
                    classif = l;
                }
            }

            if (classif == i.classification) {
                accuracy++;
            }
            System.out.println("Predicted: " + classif + " Actual: " + i.classification);
        }
        System.out.println("Accuracy=" + (accuracy / (testNodes.size())) * 100 + "%");

    }
    /**
     * reads files, code taken from helper code for assignment
     * @param filename name of the file to read
     * @return arraylist of nodes in file
     */
    public ArrayList<Node> readFiles(String filename) {
        File f = new File(filename);
        ArrayList<Node> nodes = new ArrayList<>();
        try {
            Scanner s = new Scanner(f);
            s.nextLine();// skips names
            while (s.hasNextDouble()) {
                Node n = new Node();
                double[] atts = new double[13];
                for (int i = 0; i < atts.length; i++) {
                    atts[i] = s.nextDouble();
                }
                n.setAttributes(atts);
                n.setClassification(s.nextInt());
                nodes.add(n);

            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return nodes;
    }

    /**
     * calculates range of each attribute
     * @param l list of nodes
     * @return array of ranges
     */
    public double[] rangeCalc(ArrayList<Node> l) {
        double[] ranges = new double[l.get(0).attributes.length];
        double[] max = new double[l.get(0).attributes.length];
        double[] min = new double[l.get(0).attributes.length];
        for (int i = 0; i < ranges.length; i++) {
            max[i] = Double.MIN_VALUE;
            min[i] = Double.MAX_VALUE;
        }
        for (int i = 0; i < l.size(); i++) {
            for (int j = 0; j < l.get(i).attributes.length; j++) {
                double k = l.get(i).attributes[j];
                if (k > max[j]) {
                    max[j] = k;
                }
                if (k < min[j]) {
                    min[j] = k;
                }
            }
        }
        for (int i = 0; i < ranges.length; i++) {
            ranges[i] = max[i] - min[i];
        }
        return ranges;
    }
    /**
     * euclidean distance between two nodes, min-max normalised
     * @param a first node
     * @param b node to compare distance to
     * @param ranges ranges for feature normalisation
     * @return double distance
     */
    public double euclidianDist(Node a, Node b, double[] ranges) {
        if (a.attributes.length != b.attributes.length) {
            return 0;
        }
        double sum = 0;
        for (int i = 0; i < a.attributes.length; i++) {
            sum += (Math.pow(a.attributes[i] - b.attributes[i], 2)) / (Math.pow(ranges[i], 2));
        }
        return Math.sqrt(sum);
    }
}
