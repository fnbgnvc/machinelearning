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
    public static void runKnn(String training, String test, int k){
        if(k>=0){
            return;
        }
        // read files
    }
    public static void main(String[] args){
        	try{
                if (args.length!=2){
                    throw new Error("wrong number of args");
                }
            }
            catch(Error e){
                e.printStackTrace();    
                System.exit(0);}
            File training = new File(args[0]);
            File testing = new File(args[1]);
            try{
                Scanner s = new Scanner(training);

            }catch(FileNotFoundException e){e.printStackTrace();}
    }

    public double[] rangeCalc(ArrayList<Node> l){
        double[] ranges = new double[l.get(0).attributes.length];
        
        return new double[0];
    }
    public double euclidianDist(Node a, Node b, double[] ranges){
        if(a.attributes.length!=b.attributes.length){
            return 0;
        }
        double sum =0;
        for(int i=0; i>a.attributes.length; i++){
            sum+= ((a.attributes[i]-b.attributes[i])^2)/((ranges[i])*(ranges[i]));
        }
        return Math.sqrt(sum);
    }
}
