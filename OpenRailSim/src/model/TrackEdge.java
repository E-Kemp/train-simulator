package model;
import java.awt.Point;
import java.util.Arrays;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * A class to use as edges in the graph
 * @author Elliot Jordan Kemp
 */
public class TrackEdge implements Comparable<TrackEdge> {
    
    private final double LENGTH; // km
    private final double GRADIENT;
    private double SPEED_LIM; // km/h - not final in case a temporary speed limit is imposed
    private final TrackPoint[] VERT = new TrackPoint[2];
    
    /**
     * Default constructor, should never happen
     */
    public TrackEdge() {
        this.LENGTH = 1.00;
        this.GRADIENT = 0.00;
    }

    public TrackEdge(TrackPoint[] vertices, double length, double gradient, double speedLimit) {
        this.VERT[0] = vertices[0];
        this.VERT[1] = vertices[1];
        this.LENGTH = length;
        this.GRADIENT = gradient;
        this.SPEED_LIM = speedLimit;
    }
    
    public TrackEdge(TrackPoint source, TrackPoint target, double length, double gradient, double speedLimit) {
        this.VERT[0] = source;
        this.VERT[1] = target;
        this.LENGTH = length;
        this.GRADIENT = gradient;
        this.SPEED_LIM = speedLimit;
    }
    
    public double getLength() {
        return LENGTH;
    }
    
    public double getEdgeLength() {
        return Point.distance(this.VERT[0].x, this.VERT[0].y, 
                this.VERT[1].x, this.VERT[1].y);
                
//                Math.sqrt(
//                Math.pow(this.VERT[1].x - this.VERT[0].x, 2) +
//                Math.pow(this.VERT[1].y - this.VERT[0].y, 2)
//        );
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

    public String getPointsString() {
        StringBuilder str = new StringBuilder();
        str.append('[');
        str.append(this.VERT[0].x).append(',');
        str.append(this.VERT[0].y).append("], [");
        str.append(this.VERT[1].x).append(',');
        str.append(this.VERT[1].y).append(']');
        return str.toString();
    }
    
    @Override
    public int compareTo(TrackEdge o) {
        return (int) (this.LENGTH - o.LENGTH);
    }
}
