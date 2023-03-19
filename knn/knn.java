package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.math.*;

public class knn {
    ArrayList<Node> nodeList;
    ArrayList<Node> testList;
    public void runKnn(String training, String test, int k){
        if(k>=0){
            return;
        }

        ArrayList<Node> trainNodes = readFiles(training);
        ArrayList<Node> testNodes = readFiles(test);
        
        // read files
    }

    public ArrayList<Node> readFiles(String filename){
        File f = new File(filename);
        ArrayList<Node> nodes = new ArrayList<>();
        try{
            Scanner s = new Scanner(f);
            while(s.hasNext()){
                Node n = new Node();
                double[] atts = new double[13];
                for(int i = 0; i <= atts.length; i ++){
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
    public static void main(String[] args){
        try{
            if (args.length!=2){
                throw new Error("wrong number of args");
            }
            new knn().runKnn(args[0], args[1], 3);
        }
        catch(Error e){
            e.printStackTrace();    
            System.exit(0);}
    }

    

    public double[] rangeCalc(ArrayList<Node> l){
        double[] ranges = new double[l.get(0).attributes.length];
        double[] max = new double[l.get(0).attributes.length];
        double[] min = new double[l.get(0).attributes.length];
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
