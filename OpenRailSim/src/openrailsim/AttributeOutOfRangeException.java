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
public class AttributeOutOfRangeException extends Exception {
    public AttributeOutOfRangeException(String err) {
        super(err);
    }
    
    public AttributeOutOfRangeException(String err, Throwable th) {
        super(err, th);
    }
}
