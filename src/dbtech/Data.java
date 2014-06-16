/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtech;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author P.J.H. van Heck
 */
public class Data {
    
    static String filename = "C:\\fuzzymov2.txt";
    private final List histList;
    private final List bucketList;
    private final List A;
    private final List B;

    public Data() {
        this.histList = new ArrayList();
        this.bucketList = new ArrayList();
        this.A = new ArrayList();
        this.B = new ArrayList();
    }

    public void PrintData() {
        Histogram a;
        System.out.println(this.histList.size());
        for (int i = 0; i < this.histList.size() - 1; i++) {
            a = (Histogram) this.histList.get(i);
            a.print();
            System.out.println(i);
        }
    }

    
    //-----------------------------------------------------------------------//
    //Reading data from file                                                 //
    //-----------------------------------------------------------------------//
    
    //Add the given histogram to the data.
    public void addHistogram(String ID, List ProbabilityList) {
        double EPSILON = 0.00001;
        double prob; //a probability
        double prevVal = -1; //all probabilities are between 0 and 1.
        int countVal = 0;
        List tupleList = new ArrayList();
        Histogram newHist = new Histogram(ID,tupleList);

        //sort the probabilities
        Collections.sort(ProbabilityList);

        //System.out.println(CurrentID+ProbabilityList);
        //Create histogram from list and save to datatype.
        for (Object o : ProbabilityList) {
            prob = (double) o;

            if (Math.abs(prob - prevVal) < EPSILON) {
                //if the previouse was the same number, combine.
                countVal++;
            } else {
                //save previouse probability plus frequency to data
                if (prevVal != -1) { //exclude the initial value
                    newHist.addTuple(prevVal, countVal);
                }

                //reset stats
                prevVal = prob;
                countVal = 1;
            }
        }
        //Add the last value
        newHist.addTuple(prevVal, countVal);
        //Histogram addHist = new Histogram(newHist);
        //histogram is created, add to the rest.
        this.histList.add(newHist);
        
        newHist.calculateAverage();
        newHist.print();
    }
    
    public void ReadData() {
        BufferedReader br = null;

        //Read data from file
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filename));
            String CurrentID = "";
            List ProbabilityList = new ArrayList();

            //Read through file line by line, create one histogram per ID.
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = sCurrentLine.replace("|", "_"); //splitting on | does not work
                String[] SplittedLine = sCurrentLine.split("_"); //split line in ID and probability

                if (CurrentID.equals(SplittedLine[0])) {
                    //still same histogram, keep on combining

                } else {
                    //start of new histogram, first save previous, then continue

                    //process previous histogram
                    if (!CurrentID.equals("")) {
                        addHistogram(CurrentID, ProbabilityList);
                    }
                    
                    

                    //Reset parameters
                    //ProbabilityList.clear();
                    ProbabilityList = new ArrayList();
                    
                    CurrentID = SplittedLine[0];
                }
                //collect probabilities that belong to the same ID
                ProbabilityList.add(Double.parseDouble(SplittedLine[1]));
            }
            //Add the last values
            addHistogram(CurrentID, ProbabilityList);
            System.out.println("Data imported");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                System.out.println("File closed");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //Data imported
        
    }
        
    //
    //Writing data to file
    //
        
        public void writeToFile() {
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("C:\\Users\\Public\\Documents\\out.txt");

            writer = new BufferedWriter(new FileWriter(logFile));
            for (int i = 0; i < this.bucketList.size(); i++){
                String bucket = this.bucketList.get(i).toString();
                writer.write(bucket);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (IOException e) {
            }
        }
    }
    
    //-----------------------------------------------------------------------//
    //Compressing data into buckets                                          //
    //-----------------------------------------------------------------------//
    
    public double valErr(int v,int w){
        //calculate the error for this historgram
        double err = (Double)B.get(w) - (Double)B.get(v-1) - (Math.pow(((Double)A.get(w) - (Double)A.get(v-1)),2))/(w-v+1);
        //take the absolute error to compare between negative and positive errors
        return Math.abs(err);
    }
    
    public double bOpt(int s, int e, int w,int T, double min){
        //double min = Double.MAX_VALUE;
        if (T < 0){
            return 0.0;
        }
        for (int v = 0; v < w-1; v++){
            //Recurse over bOpt to find the optimal error
            double temp = bOpt(s,e,w,T-1,min) + valErr(s,e);
            min = Math.min(min, temp);
        }
        return min;
    }
    
    //Calculates the optimal histogram
    //m = bucketSize
    //bucketNr
    //V = dimention of histogram
    public List hOpt(int m, int bucketNr, int V, List min){
       
        //return 0 if recursion has reached its end
        if(m < 1){
            List recurEnd = new ArrayList();
            recurEnd.add(0.0);
            recurEnd.add(0);
            recurEnd.add(0);
            return recurEnd;
        }
        //int minS = 0,minE = 0;
        for (int k = (bucketNr*m); k < ((bucketNr+1)*(m)); k++){
            double temp = 0;
            //Call bOpt to calculate the error
            temp += bOpt(k+1,m,V+1,V,Double.MAX_VALUE);
            //If a better representative was found, save it
            if (temp < (Double)min.get(0)){
                min.set(0, temp);
                min.set(1, k+1);
                min.set(2, m);
            }
        }
        return min;
    }
    
    public void sumSquaredPreCompute(){
        for(int i=0; i<histList.size(); i++){
            Histogram hist = (Histogram) histList.get(i);
            for(int j=0; j<hist.size(); j++){
                this.A.add(hist.getTuple(j).prob);
                this.B.add(Math.pow(hist.getTuple(j).prob,2));
            }
        }
    }
    
    // compress all the data into buckets
    public void compressIntoNBuckets(int nrOfBuckets){
        System.out.println("Start creating buckets");
        //precompute values
        sumSquaredPreCompute();
        
        int bucketSize = (int)Math.ceil(histList.size()/nrOfBuckets);
        
        //for each bucket, compute the representative
        for (int currentBucketNr = 0; currentBucketNr < nrOfBuckets; currentBucketNr++){
            List bucket = new ArrayList();
            //initialize bucket, error = max, others are representative
            bucket.add(Double.MAX_VALUE);
            bucket.add(0);
            bucket.add(0);
            
            //Calculate optimal representative for this bucket
            bucket = hOpt(bucketSize, currentBucketNr, 5, bucket);
            bucketList.add(bucket);
            System.out.println(currentBucketNr+" buckets created. "+bucket);
            
            
        }
        System.out.println("All buckets created");
        writeToFile();
           
    }
    

    
    //-----------------------------------------------------------------------//
    //Query buckets                                                          //
    //-----------------------------------------------------------------------//
    
    public void normalSelect(){
        
    }
    
    public void bucketSelect(){
        
    }
}
