/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtech;

import java.io.BufferedReader;
import java.io.FileReader;
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

    public Data() {
        this.histList = new ArrayList();
        this.bucketList = new ArrayList();
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

                    //process previous
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
        
        newHist.print();
    }
    
    //Compress the data using either the B bucket or the T term metric
    public void compressData(int nrOfBuckets) {
        int bucketSize = (int) Math.ceil(histList.size()/nrOfBuckets);
        
        histList.size();
        bucketList.size();
        //precompute Sum-Squared Error values in O(VN) time
        
        //compute buckets
        for (int i = 0; i < bucketSize; i++ ){
            List bucketList = new ArrayList();
            int s = i*bucketSize;
            int e = (i+1)*bucketSize - 1;
            
            //collect all histograms in range
            for (int j = s; j < e; i++){
                bucketList.add(histList.get(j));
                
                
            }
            // bucketList contains all histograms
            
            //find representative data
            
            //add representative histogram to bucketList
            
            //clear list and start with the next bucket.
            bucketList.clear();
        }
    }
    
    public double ValErr(Bucket b,int v,int w){
        
        return 0;
    }
    
    //Put histograms in buckets
    public void sumSquaredBuckets( ){
        
    }
}
