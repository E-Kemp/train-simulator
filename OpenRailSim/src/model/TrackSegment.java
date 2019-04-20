package model;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * A class to use as edges in the graph
 * @author Elliot Jordan Kemp
 */
public class TrackSegment implements Comparable<TrackSegment> {
    
    private final double LENGTH; // km
    private final double GRADIENT;
    private double SPEED_LIM; // km/h - not final in case a temporary speed limit is imposed
    private final TrackPoint[] VERT = new TrackPoint[2];
    
    /**
     * Default constructor, should never happen
     */
    public TrackSegment() {
        this.LENGTH = 1.00;
        this.GRADIENT = 0.00;
    }

    public TrackSegment(TrackPoint[] vertices, double length, double gradient, double speedLimit) {
        this.VERT[0] = vertices[0];
        this.VERT[1] = vertices[1];
        this.LENGTH = length;
        this.GRADIENT = gradient;
        this.SPEED_LIM = speedLimit;
    }
    
    public TrackSegment(TrackPoint source, TrackPoint target, double length, double gradient, double speedLimit) {
        this.VERT[0] = source;
        this.VERT[1] = target;
        this.LENGTH = length;
        this.GRADIENT = gradient;
        this.SPEED_LIM = speedLimit;
    }
    
    public double getLength() {
        return LENGTH;
    }

    public double getGradient() {
        return GRADIENT;
    }
    
    public double getSpeedLimit() {
        return this.SPEED_LIM;
    }
    
    public double changeSpeedLimit(double newLim) {
        return this.SPEED_LIM = newLim;
    }
    
    public TrackPoint getTarget(TrackPoint p) {
        if(this.VERT[0].compareTo(p) == 0)
            return this.VERT[1];
        else
            return this.VERT[0];
    }
    
    public boolean hasPoint(TrackPoint p) {
        for(TrackPoint v : this.VERT)
            if(v.compareTo(p) == 0)
                return true;
        return false;
    }
    
    public TrackPoint[] getPoints() {
        return this.VERT;
    }

    @Override
    public int compareTo(TrackSegment o) {
        return (int) (this.LENGTH - o.LENGTH);
    }
}
