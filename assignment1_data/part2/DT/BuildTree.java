
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
public class BuildTree {
    
    int numCategories;
    int numAtts;
    Set<String> categoryNames;
    List<String> attNames;
    List<Instance> allInstances;
    Node root;

    BuildTree(String trainingData, String testData){
        readDataFile(trainingData);
        root = buildTree(allInstances, attNames);
        root.report("");
    }
    public static void main(String[] args){
        String trainingData = System.getProperty("user.dir") + "\\assignment1_data\\part2\\golf-training.txt";
        String testData = System.getProperty("user.dir") + "\\assignment1_data\\part2\\golf-test.txt";
        new BuildTree(trainingData, testData);
        /*if(args.length==2){
            String trainingData = args[0];
            String testData = args[1];
            new BuildTree(trainingData, testData);

        }*/
    }


    void readDataFile(String fname) {
        /* format of names file:
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

            allInstances = readInstances(din);
            din.close();

            categoryNames = new HashSet<>();
            for (Instance i : allInstances) {
                categoryNames.add(i.category);
            }
            numCategories = categoryNames.size();
            System.out.println(numCategories + " categories");

            for (Instance i : allInstances) {
                System.out.println(i);
            }
        } catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }

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

    public Node buildTree(List<Instance> instances, List<String> attributes){
        if(instances.size()==0){
            return new LeafNode();
            //return leaf node containing name 
            //and probability of most probable class across whole training set
        }
        //else if: instances all belong to same class
        //return leaf node that contains name of the class and prob 1
        //else find best attribute:
        /* 
         * for each attribute:
         * separate instances into 2 sets: true & false for the attribute
         * (attributes are held in instance as list of booleans)
         * compute purity
         * if weighted average purity of the 2 is best so far:
         * best att=this att
         * bestInstsTrue = set of true instances
         * bestInstsFalse = set of false instances
         * 
         * then left=buildtree(bestinststrue, attributes without bestAttr)
         * right = buildtree(bestinstsfalse, attributes without bestAttr)
         * return node containing (bestatt, left, right)
         */
        return null;
    }
    private void baseline(){
        HashMap<String, Integer> m = new HashMap<String,Integer>();
        for(String c : categoryNames){
            m.put(c,0);
        }
        for(Instance i : allInstances){
            m.replace(i.category, m.get(i.category)+1);
        }
        

    }
}
    
