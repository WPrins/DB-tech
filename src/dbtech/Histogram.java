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
    
    //initialize
    public Histogram(String ID, List tupleList){
        this.tupleList = tupleList;
        this.ID = ID;
    }
    
      
    public void addTuple(double prob, int frequency){
        Tuple tuple = new Tuple(prob, frequency);
        this.tupleList.add(tuple);
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
            //System.out.print("("+t.frequency+" - "+t.prob+"),");
        }
        //System.out.println();
    }
}
