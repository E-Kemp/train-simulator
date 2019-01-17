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
    private final String code;
    
    
    public Point(String code) {
        this.code = code;
    }
    
    public String getTIPLOC() {
        return this.code;
    }
    
    @Override
    public abstract String toString();
    
    
}
