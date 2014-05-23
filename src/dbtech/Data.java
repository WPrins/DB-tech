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
    private static List histList;
    
    public Data(){
        histList = new ArrayList();
    }
    
    public void PrintData(){
        Histogram a;
        System.out.println(histList.size() );
        for(int i = 0; i<histList.size()-1; i++){
            a = (Histogram)histList.get(i);
            a.print();
            System.out.println(i);
        }
    }
    
    public void ReadData(){
        BufferedReader br = null;
        
        //Read data from file
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filename));
            String CurrentID = "";
            List ProbabilityList = new ArrayList();
            
            //Read through file line by line, create one histogram per ID.
			while ((sCurrentLine = br.readLine()) != null) {  
                sCurrentLine=sCurrentLine.replace("|", "_"); //splitting on | does not work
                String[] SplittedLine = sCurrentLine.split("_"); //split line in ID and probability
 				
                if (CurrentID.equals(SplittedLine[0])) {
                    //still same histogram, keep on combining
                    
                } else {
                    //start of new histogram, first save previous, then continue
                    
                    //process previous
                    if (!CurrentID.equals("")){
                        addHistogram(CurrentID, ProbabilityList);
                    }
                    
                    //Reset parameters
                    ProbabilityList.clear();
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
        }finally {
            try {
				if (br != null)br.close();
                System.out.println("File closed");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
        }
    }
    
    //Add the given histogram to the data.
    public void addHistogram(String ID, List ProbabilityList){
        double EPSILON = 0.00001;
        double prob; //a probability
        double prevVal = -1; //all probabilities are between 0 and 1.
        int countVal = 0;
        Histogram newHist = new Histogram(ID);
            
        //sort the probabilities
        Collections.sort(ProbabilityList);
        
        //System.out.println(CurrentID+ProbabilityList);
        
        //Create histogram from list and save to datatype.
        for (Object o : ProbabilityList) {
            prob = (double)o;
            
            if (Math.abs(prob-prevVal) < EPSILON){
                //if the previouse was the same number, combine.
                countVal++;
            } else {
                //save previouse probability plus frequency to data
                if (prevVal != -1){ //exclude the initial value
                    newHist.addTuple(prevVal, countVal);
                }
                
                //reset stats
                prevVal = prob;
                countVal = 1;
            }
        }
        //Add the last value
        newHist.addTuple(prevVal, countVal);
        
        //histogram is created, add to the rest.
        histList.add(newHist);
        newHist.print();
    }
    
}