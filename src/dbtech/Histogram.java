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
    private static List tupleList;
    private static String ID;
    
    //initialize
    public Histogram(String ID){
        tupleList = new ArrayList();
        this.ID = ID;
    }
    
    public void addTuple(double prob, int frequency){
        Tuple tuple = new Tuple(prob, frequency);
        tupleList.add(tuple);
    }
    
    public void print(){
        System.out.print(ID+" - ");
        for (Object o : tupleList) {
            Tuple t = (Tuple)o;
            System.out.print("("+t.frequency+" - "+t.prob+"),");
        }
        System.out.println();
    }
}
