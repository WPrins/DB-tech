/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbtech;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Wessel
 */
public class Main {
    static String filename = "C:\\fuzzymov2.txt";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello World");
        
        ReadData();
        
    }
    
    public static void ReadData(){
        BufferedReader br = null;
        
        //Read data
        try {
            String sCurrentLine;
 
			br = new BufferedReader(new FileReader(filename));
            String CurrentID = "";
            List ProbabilityList = new ArrayList();
			while ((sCurrentLine = br.readLine()) != null) {  
                sCurrentLine=sCurrentLine.replace("|", "_"); //splitting on | does not work
                String[] SplittedLine = sCurrentLine.split("_");
 				//System.out.println(SplittedLine[0]+" - "+SplittedLine[1]);
                if (CurrentID.equals(SplittedLine[0])) {
                    //still same histogram
                    ;
                } else {
                    // new histogram
                    //process previouse
                    ProcessHistogram(CurrentID, ProbabilityList);
                    
                    //Reset
                    ProbabilityList.clear();
                    CurrentID = SplittedLine[0];
                }
                ProbabilityList.add(Double.parseDouble(SplittedLine[1]));
			}
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
        }
    }
    
    public static void ProcessHistogram(String CurrentID, List ProbabilityList){
        //ToDo
        double EPSILON = 0.00001;
        double prob;
        double prevVal = -1; //all probabilities are between 0 and 1.
        int countVal = 0;
                
        Collections.sort(ProbabilityList);
        
        //Create histogram from list and save to datatype.
        for (Object o : ProbabilityList) {
            prob = (double)o;
            if (Math.abs(prob-prevVal) < EPSILON){
                //Same number as previouse
                countVal++;
            } else {
                //save previouse value to data
                
                
                //reset stats
                prevVal = prob;
                countVal = 1;
            }
        }
        System.out.println(CurrentID+ProbabilityList);
    }
    
}
