package model;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * A class to use as edges in the graph
 * @author Elliot Jordan Kemp
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
