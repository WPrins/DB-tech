/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtech;

/**
 *
 * @author P.J.H. van Heck
 */
public class Tuple<prob, frequency> {
    public final double prob; 
    public final int frequency; 
    
    public Tuple(double prob, int frequency) { 
        this.prob = prob; 
        this.frequency = frequency; 
    } 
}
