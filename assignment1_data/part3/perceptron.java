package assignment1_data.part3;

import java.io.*;
import java.util.Scanner;

public class perceptron {
    public static void main(String[] args){
        //args: name of data file
                
        try{
            if (args.length!=1){
                throw new Error("wrong number of args");
            }
            String path = System.getProperty("user.dir") + "\\assignment1_data\\part3\\" + args[0];
            
            new perceptron(path);
        }
        catch(Error e){
            e.printStackTrace();    
            System.exit(0);}
        
    }


    //until the perceptron is always right (or fails to converge within 100 iter.)
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
    perceptron(String path){
        File f = new File(path);
        try{
            Scanner s = new Scanner(f);
            

            s.close();

        }catch(FileNotFoundException e){e.printStackTrace();}
        //read data: features 1-34 and string class

    }
    public void runPerceptron(String path){

    }
}
