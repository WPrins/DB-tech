/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtech;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author P.J.H. van Heck
 */
public class Histogram {
 
    //Histogram concists of probability/frequency tuples
    private List tupleList;
    private String ID;
    private double singleRepresentative;
    private double sumErrorToSingleRepresentative;
    private double averageError;
    
    //initialize
    public Histogram(String ID, List tupleList){
        this.tupleList = tupleList;
        this.ID = ID;
    }
    
      
    public void addTuple(double prob, int frequency){
        Tuple tuple = new Tuple(prob, frequency);
        this.tupleList.add(tuple);
    }
    
    //Calculate the single representative and the sum of squared error to that
    public void calculateAverage(){
                
        //Find the single representative, the prob with highest frequency.
        int highestFreq = 0;
        for (Object o : this.tupleList) {
            Tuple tuple = (Tuple)o;
            
            //If frequency is higher, update fields
            if (tuple.frequency > highestFreq){
                highestFreq = tuple.frequency;
                this.singleRepresentative = tuple.prob;
            }   
        }
        
        //Calculate Sum of Squared Error
        double sse = 0;
        for (Object o : this.tupleList){
            Tuple tuple = (Tuple)o;
            
            sse += ( (tuple.prob-this.singleRepresentative) * (tuple.prob-this.singleRepresentative) );
        }
        
        //Save globaly
        this.sumErrorToSingleRepresentative = sse;
        System.out.println(sse);
    }
    
    public int size(){
        return this.tupleList.size();
    }
    
    public Tuple getTuple(int i){
        return (Tuple) this.tupleList.get(i);
    }
    
    public void print(){
        //System.out.print(ID+" - ");
        for (Object o : this.tupleList) {
            Tuple t = (Tuple)o;
            System.out.print("("+t.frequency+" - "+t.prob+"),");
        }
        //System.out.println();
    }
}
