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
    private static Data dataClass;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        dataClass = new Data();
        System.out.println("Hello World");
        
        //read data
        dataClass.ReadData();
        
        //Something still goes wrong, as shows this:
        //dataClass.PrintData();
        
        //compress data
        //dataClass.sumSquaredBuckets(1);
        
        //query data on raw and compressed data.
        System.out.println("Start printing read data");
        
        dataClass.PrintData();
        
        System.out.println("Good night");
    }
    
}
