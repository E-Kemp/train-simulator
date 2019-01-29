/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openrailsim;

/**
 *
 * @author mrp15ndu
 */
public abstract class Point {
    private final String CODE;
    
    public Point() {
        this.CODE = "SET ME";
    }
    
    public Point(String code) {
        this.CODE = code;
    }
    
    public String getTIPLOC() {
        return this.CODE;
    }
    
    @Override
    public abstract String toString();
    
    
}
