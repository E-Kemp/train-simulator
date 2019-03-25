package model;

import java.util.Iterator;
import java.util.Set;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A vertex in the graph data structure
 * @author Elliot Jordan Kemp
 */
public class TrackPoint extends Point implements Comparable<TrackPoint> {
    
    private final String CODE;
    private final List<TrackSegment> EDGES = new ArrayList();
    //mainly used for traversal
    private boolean MARK = false; // used for traversal
    
    public TrackPoint() {
        //Validate this later
        super();
        this.CODE = "SET ME";
    }
    
    /**
     * Constructor for the root
     * @param code
     * @param x
     * @param y 
     */
    public TrackPoint(String code, int x, int y) {
        super(x, y);
        this.CODE = code;
    }

    public TrackPoint(TrackSegment source, TrackSegment target, String code, int x, int y) {
        super(x,y);
        this.CODE = code;
        this.EDGES.add(0, source); // Ensure source is placed at index 0;
        this.EDGES.add(target);
    }
    
    public TrackPoint(TrackSegment source, List<TrackSegment> target, String code, int x, int y) {
        super(x,y);
        this.CODE = code;
        this.EDGES.add(0, source);
        this.EDGES.addAll(target);
    }
    
    public TrackPoint(TrackPoint p, String code, int x, int y, double length, double gradient) {
        super(x, y);
        this.CODE = code;
        this.EDGES.add(new TrackSegment(this, p, length, gradient));
    }
    
    public boolean addEdge(TrackPoint p, double length, double gradient) {
        return this.EDGES.add(new TrackSegment(this, p, length, gradient));
    }
    
    public boolean addEdge(TrackSegment e) {
        return this.EDGES.add(e);
    }
    
    public String getCode() {
        return this.CODE;
    }
    
    public List<TrackSegment> getEdges() {
        return this.EDGES;
    }
    
    public boolean isMarked() {
        return this.MARK;
    }
    
    public void mark() {
        this.MARK = true;
    }
    public void unmark() {
        this.MARK = false;
    }
    
    /*
    This shouldn't be needed?
    */
//    public boolean addEdge(TrackSegment t) {
//        
//        return false;
//    }
    
    public static String toString(Set<TrackPoint> points) {
        StringBuilder str = new StringBuilder();
        Iterator<TrackPoint> it = points.iterator();
        it.forEachRemaining(p -> str.append(p.getCode()).append(','));
        return str.toString();
    }

    @Override
    public int compareTo(TrackPoint o) {
        return this.CODE.compareTo(o.getCode());
    }
}
