package model;

/**
 * Custom exception class for an attribute that's out of range
 * @author Elliot Jordan Kemp
 */
public class AttributeOutOfRangeException extends Exception {
    public AttributeOutOfRangeException(String err) {
        super(err);
    }
    
    public AttributeOutOfRangeException(String err, Throwable th) {
        super(err, th);
    }
}
