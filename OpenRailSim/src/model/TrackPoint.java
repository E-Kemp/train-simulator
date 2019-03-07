package model;

import java.util.Iterator;
import java.util.Set;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * A vertex in the graph data structure
 * @author Elliot Jordan Kemp
 */
public class TrackPoint extends Point{
    
    private final String CODE;
    private final List<TrackSegment> SOURCE = new LinkedList();
    private final List<TrackSegment> TARGET = new LinkedList();
    //mainly used for traversal
    private boolean TERMINATE = true;
    
    public TrackPoint() {
        //Validate this later
        super();
        this.CODE = "SET ME";
    }
    
    public TrackPoint(TrackSegment source, TrackSegment target, String code, int x, int y) {
        super(x,y);
        this.CODE = code;
        this.SOURCE.add(source);
        this.TARGET.add(target);
    }
    
    public boolean terminal() {
        return this.TERMINATE;
    }
    
    public String getCode() {
        return this.CODE;
    }
    
    public List<TrackSegment> getSources() {
        return this.SOURCE;
    }
    public List<TrackSegment> getTargets() {
        return this.TARGET;
    }
    public TrackSegment getSource() {
        return this.SOURCE.get(0);
    }
    public TrackSegment getTarget() {
        return this.TARGET.get(0);
    }
    
    public boolean terminate() {
        return this.TERMINATE = !this.TERMINATE;
    }
    public boolean addSource(TrackSegment t) {
        return this.SOURCE.add(t);
    }
    public boolean addTarget(TrackSegment t) {
        return this.TARGET.add(t);
    }
    
    public boolean isPoint(String code) {
        return this.CODE.equalsIgnoreCase(code);
    }
    
    
    
    public static String toString(Set<TrackPoint> points) {
        StringBuilder str = new StringBuilder();
        Iterator<TrackPoint> it = points.iterator();
        it.forEachRemaining(p -> str.append(p.getCode()).append(','));
        return str.toString();
    }
}
