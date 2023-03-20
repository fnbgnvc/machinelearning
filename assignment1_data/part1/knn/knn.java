package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.math.*;

public class knn {
    ArrayList<Node> nodeList;
    ArrayList<Node> testList;
    public void runKnn(String training, String test, int k){
        if(k<=0){
            return;
        }
        ArrayList<Node> trainNodes = readFiles(training);
        ArrayList<Node> testNodes = readFiles(test);
        double[] ranges = rangeCalc(trainNodes);
        
        for(Node i : testNodes){
            HashMap<Double, Node> h = new HashMap<>();
            for(Node j : trainNodes){
                double dist = euclidianDist(i, j, ranges);
                h.put(dist, j);
            }
            List<Double> hlist = h.keySet().stream().collect(Collectors.toList());
            Collections.sort(hlist);
            System.out.println(hlist);
            //this is where the k nearest neighbours are computed. distance to each neighbour is calculated,
            //then the k nearest are taken,and the most common classification is taken
        }
        
        // read files
    }

    public int kNeighbourClassification(Node n, ArrayList<Node> neighbours, int k){
        
        return 0;
    }

    public ArrayList<Node> readFiles(String filename){
        File file = new File(filename);
        System.out.println(file.getAbsolutePath());

        File f = new File(filename);
        ArrayList<Node> nodes = new ArrayList<>();
        try{
            Scanner s = new Scanner(f);
            s.nextLine();//skips names
            while(s.hasNextDouble()){
                Node n = new Node();
                double[] atts = new double[13];
                for(int i = 0; i < atts.length; i ++){
                    atts[i] = s.nextDouble();
                }
                n.setAttributes(atts);
                n.setClassification(s.nextInt());
                nodes.add(n);

            }   
            s.close();
        }catch(FileNotFoundException e){e.printStackTrace();}
            return nodes;
    }


    public static void main(String[] args) throws Exception{
        
        
        String path1 = System.getProperty("user.dir") + "\\assignment1_data\\part1\\wine-training.txt";
        String path2 = System.getProperty("user.dir") + "\\assignment1_data\\part1\\wine-test.txt";
        
        new knn().runKnn(path1, path2, 3);
        /*try{
            if (args.length!=2){
                throw new Error("wrong number of args");
            }
            new knn().runKnn(args[0], args[1], 3);
        }
        catch(Error e){
            e.printStackTrace();    
            System.exit(0);}*/
    }

    

    public double[] rangeCalc(ArrayList<Node> l){
        double[] ranges = new double[l.get(0).attributes.length];
        double[] max = new double[l.get(0).attributes.length];
        double[] min = new double[l.get(0).attributes.length];
        for(int i =0; i<ranges.length;i++){
            max[i]=Double.MIN_VALUE;
            min[i]=Double.MAX_VALUE;
        }
        for(int i =0; i<l.size(); i++){
            for(int j = 0; j<l.get(i).attributes.length; j++){
                double k = l.get(i).attributes[j];
                if(k>max[j]){max[j]=k;}
                if(k<min[j]){min[j]=k;}
            }
        }
        for(int i =0; i<ranges.length;i++){
            ranges[i]=max[i]-min[i];
        }
        return ranges;
    }

    public double euclidianDist(Node a, Node b, double[] ranges){
        if(a.attributes.length!=b.attributes.length){
            return 0;
        }
        double sum =0;
        for(int i=0; i>a.attributes.length; i++){
            sum+= (Math.pow(a.attributes[i]-b.attributes[i], 2))/(Math.pow(ranges[i],2));
        }
        return Math.sqrt(sum);
    }
}
