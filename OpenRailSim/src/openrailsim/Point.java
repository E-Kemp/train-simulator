/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openrailsim;

import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author mrp15ndu
 */
public class Point {
    private final String CODE;
    
    public Point() {
        //Validate this later
        this.CODE = "SET ME";
    }
    
    public Point(String code) {
        this.CODE = code;
    }
    
    public String getCode() {
        return this.CODE;
    }
    
    public boolean isPoint(String code) {
        return this.CODE.equalsIgnoreCase(code);
    }
    
    public static String toString(Set<Point> points) {
        StringBuilder str = new StringBuilder();
        Iterator<Point> it = points.iterator();
        it.forEachRemaining(p -> str.append(p.getCode()).append(','));
        return str.toString();
    }
}
