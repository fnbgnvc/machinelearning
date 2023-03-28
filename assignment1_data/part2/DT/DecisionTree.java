
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DecisionTree {

    int numCategories;
    int numAtts;
    List<String> categoryNames;
    List<String> attNames;
    List<Instance> allInstances;
    Node root;
    String baselineCategory;
    double baselineProbability;

    DecisionTree(String trainingData, String testData) {
        readDataFile(trainingData);
        baseline();
        root = buildTree(allInstances, attNames);
        root.report("");
        readDataFile(testData);
        testClassify();
    }

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new Error("wrong number of args");
            }
            String trainingData = System.getProperty("user.dir") + "\\assignment1_data\\part2\\" + args[0];
            String testData = System.getProperty("user.dir") + "\\assignment1_data\\part2\\" + args[1];
            new DecisionTree(trainingData, testData);
        } catch (Error e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * taken from helper code given in assignment
     * 
     * @param fname name of file
     */
    void readDataFile(String fname) {
        /*
         * format of names file:
         * names of attributes (the first one should be the class/category)
         * category followed by true's and false's for each instance
         */
        System.out.println("Reading data from file " + fname);
        try {
            Scanner din = new Scanner(new File(fname));
            this.attNames = new ArrayList<>();
            Scanner s = new Scanner(din.nextLine());
            // Skip the "Class" attribute.
            s.next();
            while (s.hasNext()) {
                attNames.add(s.next());
            }
            this.numAtts = attNames.size();
            System.out.println(numAtts + " attributes");
            System.out.println(this.attNames);

            allInstances = readInstances(din);
            din.close();

            categoryNames = new ArrayList<String>();
            for (Instance i : allInstances) {
                if (!categoryNames.contains(i.category)) {
                    categoryNames.add(i.category);
                }
            }
            numCategories = categoryNames.size();
            System.out.println(numCategories + " categories");
        } catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }

    /**
     * taken from helper code given in assignment
     * 
     * @param din
     * @return
     */
    private List<Instance> readInstances(Scanner din) {
        /* instance = classname and space separated attribute values */
        List<Instance> instances = new ArrayList<>();
        while (din.hasNext()) {
            Scanner line = new Scanner(din.nextLine());
            instances.add(new Instance(line.next(), line));
        }
        System.out.println("Read " + instances.size() + " instances");
        return instances;
    }

    public Node buildTree(List<Instance> instances, List<String> attributes) {
        List<String> myAtts = new ArrayList<String>();
        myAtts.addAll(attributes);
        if (instances.isEmpty()) {
            return new LeafNode(baselineCategory, baselineProbability);
            // return leaf node containing name
            // and probability of most probable class across whole training set
        } else if (allInstancesMatch(instances)) {
            // else if: instances all belong to same class
            // return leaf node that contains name of the class and prob 1
            return new LeafNode(instances.get(0).getCategory(), 1);
        } else if (attributes.isEmpty()) {
            // name and probability of majority class of instances
            int[] tally = {
                    (int) instances.stream().filter(a -> a.getCategory().equals(instances.get(0).getCategory()))
                            .count(),
                    (int) instances.stream().filter(a -> !a.getCategory().equals(instances.get(0).getCategory()))
                            .count() };
            int numOccuring = 0;
            String category = null;
            for (int i = 0; i < tally.length; i++) {
                if (tally[i] > numOccuring) {
                    numOccuring = tally[i];
                    category = categoryNames.get(i);
                }
            }
            return new LeafNode(category, numOccuring
                    / (double) instances.size());
        } else {
            /*
             * else find best attribute:
             * for each attribute:
             * separate instances into 2 sets: true & false for the attribute
             * (attributes are held in instance as list of booleans)
             */
            double bestPBI = Integer.MAX_VALUE;
            List<Instance> bestInstsTrue = new ArrayList<Instance>();
            List<Instance> bestInstsFalse = new ArrayList<Instance>();
            String best = baselineCategory;
            // try every remaining attribute as split
            for (String att : myAtts) {
                List<Instance> sTrue = new ArrayList<Instance>();
                List<Instance> sFalse = new ArrayList<Instance>();
                for (Instance i : instances) {
                    if (i.getAtt(attNames.indexOf(att)) && !sTrue.contains(i)) {
                        sTrue.add(i);
                    } else if (!i.getAtt(attNames.indexOf(att)) && !sFalse.contains(i)) {
                        sFalse.add(i);
                    }
                }
                double wavg = weightedPBI(sTrue, sFalse);
                if (wavg < bestPBI) {
                    bestPBI = wavg;
                    bestInstsTrue = sTrue;
                    bestInstsFalse = sFalse;
                    best = att;
                }

            }
            myAtts.remove(best);
            Node left = buildTree(bestInstsTrue, myAtts);
            Node right = buildTree(bestInstsFalse, myAtts);
            return new SplitNode(best, left, right);
        }
    }

    /**
     * weighted probability-based impurity of a feature split
     * 
     * @param instLeft  instances in left (true) group
     * @param instRight instances in right (false) group
     * @return double impurity
     */
    private double weightedPBI(List<Instance> instLeft, List<Instance> instRight) {
        double totalInstance = (double) instLeft.size() + instRight.size();
        double impurityL = PBIone(instLeft);
        double impurityR = PBIone(instRight);
        // weighted impurity
        double wimpL = impurityL * ((double) instLeft.size() / totalInstance);
        double wimpR = impurityR * ((double) instRight.size() / totalInstance);
        double wAvg = wimpL + wimpR;
        return wAvg;

        /*
         * wAvg(PBI)= N(L)xPBI(L) + N(R)xPBI(R)
         * where N= #instances/#inst in parent (aka left+right)
         * and PBI = mn/(m+n)^2 (m = class A no of inst, n = class B no of inst)
         */
    }

    /**
     * impurity of one group of instances
     * 
     * @param insts instances to test
     * @return double probability-based impurity
     */
    private double PBIone(List<Instance> insts) {
        if (allInstancesMatch(insts)) {
            return 0;
        }
        String cat1 = insts.get(0).getCategory();
        double count1 = insts.stream().filter(a -> a.getCategory().equals(cat1)).count();
        double count2 = insts.stream().filter(a -> !a.getCategory().equals(cat1)).count();

        double impurity = (count1 / (double) insts.size()) * (count2 / (double) insts.size());
        return impurity;

    }

    private boolean allInstancesMatch(List<Instance> instances) {
        for (Instance i : instances) {
            for (Instance j : instances) {
                if (!i.getCategory().equals(j.getCategory())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void baseline() {
        HashMap<String, Integer> m = new HashMap<String, Integer>();
        for (String c : categoryNames) {
            m.put(c, 0);
        }
        for (Instance i : allInstances) {
            m.replace(i.category, m.get(i.category) + 1);
        }
        String baselineCat = "";
        double mostCommon = Double.MIN_VALUE;
        for (String i : categoryNames) {
            if (m.get(i) > mostCommon) {
                baselineCat = i;
                mostCommon = m.get(i);
            }
        }
        this.baselineCategory = baselineCat;
        this.baselineProbability = mostCommon / m.entrySet().stream().mapToInt(a -> a.getValue()).sum();
    }

    private void testClassify() {
        // classify each instance
        int correct = 0;
        for (Instance instance : allInstances) {
            String clasf = classify(instance, root);
            if (clasf.equals(instance.getCategory())) {
                correct++;
            }
            System.out.println("predicted: " + clasf + " actual: " + instance.getCategory());
        }
        double perctrue = correct / (double) allInstances.size();
        System.out.println("Accuracy =" + perctrue * 100 + "% " + correct + "/" + allInstances.size());
    }

    private String classify(Instance i, Node n) {
        // check if leaf or split node.
        // return baselineCategory;
        if (n instanceof LeafNode) {
            LeafNode ln = (LeafNode) n;
            return ln.className;
        }
        if (n instanceof SplitNode) {
            SplitNode sn = (SplitNode) n;
            int attrNum = attNames.indexOf(sn.attName);
            if (i.getAtt(attrNum)) {
                return classify(i, sn.left);
            } else {
                return classify(i, sn.right);
            }
        }
        return "";
    }
}
