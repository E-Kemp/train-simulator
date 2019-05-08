package model;

import java.awt.Point;


/**
 * A class to use as edges in the graph
 * @author 100128483
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

    /**
     * Constructor with all parameters
     * @param vertices
     * @param length
     * @param gradient
     * @param speedLimit
     */
    public TrackEdge(TrackPoint[] vertices, double length, double gradient, double speedLimit) {
        this.VERT[0] = vertices[0];
        this.VERT[1] = vertices[1];
        this.LENGTH = length;
        this.GRADIENT = gradient;
        this.SPEED_LIM = speedLimit;
    }
    
    /**
     * Constructor with all parameters (separated TrackPoints)
     * @param source
     * @param target
     * @param length
     * @param gradient
     * @param speedLimit
     */
    public TrackEdge(TrackPoint source, TrackPoint target, double length, double gradient, double speedLimit) {
        this.VERT[0] = source;
        this.VERT[1] = target;
        this.LENGTH = length;
        this.GRADIENT = gradient;
        this.SPEED_LIM = speedLimit;
    }
    
    /**
     * @return length of the track
     */
    public double getLength() {
        return LENGTH;
    }
    
    /**
     * @return length of the line on the map
     */
    public double getEdgeLength() {
        return Point.distance(this.VERT[0].x, this.VERT[0].y, 
                this.VERT[1].x, this.VERT[1].y);
    }

    /**
     * @return gradient of the track
     */
    public double getGradient() {
        return GRADIENT;
    }
    
    /**
     * @return speed limit of the track
     */
    public double getSpeedLimit() {
        return this.SPEED_LIM;
    }
    
    /**
     * Change the speed limit of the track
     * @param newLim
     * @return the new speed limit
     */
    public double changeSpeedLimit(double newLim) {
        return this.SPEED_LIM = newLim;
    }
    
    /**
     * @param p
     * @return the opposite point to the provided point
     */
    public TrackPoint getTarget(TrackPoint p) {
        if(this.VERT[0].compareTo(p) == 0)
            return this.VERT[1];
        else
            return this.VERT[0];
    }
    
    /**
     * @param p
     * @return whether the track includes point p
     */
    public boolean hasPoint(TrackPoint p) {
        for(TrackPoint v : this.VERT)
            if(v.compareTo(p) == 0)
                return true;
        return false;
    }
    
    /**
     * @return both points of the track
     */
    public TrackPoint[] getPoints() {
        return this.VERT;
    }

    /**
     * @return both points of the track as a string
     */
    public String getPointsString() {
        StringBuilder str = new StringBuilder();
        str.append('[');
        str.append(this.VERT[0].x).append(',');
        str.append(this.VERT[0].y).append("], [");
        str.append(this.VERT[1].x).append(',');
        str.append(this.VERT[1].y).append(']');
        return str.toString();
    }
    
    /**
     * Compare the track length
     * @param o
     * @return which order the track is compared to the supplied track
     */
    @Override
    public int compareTo(TrackEdge o) {
        return (int) (this.LENGTH - o.LENGTH);
    }
}
