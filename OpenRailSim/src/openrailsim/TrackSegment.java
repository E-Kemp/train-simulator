/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openrailsim;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 *
 * @author mrp15ndu
 */
public class TrackSegment extends DefaultWeightedEdge {
    
    private final double LENGTH;
    private final double GRADIENT;
    

    public TrackSegment() {
        this.LENGTH = 1.00;
        this.GRADIENT = 0.00;
    }
    
    // SORT THIS SHIT OUT
    
    public TrackSegment(double length, double gradient) {
        this.LENGTH = length;
        this.GRADIENT = gradient;
    }
    
    public double getLength() {
        return LENGTH;
    }

    public double getGradient() {
        return GRADIENT;
    }
}
