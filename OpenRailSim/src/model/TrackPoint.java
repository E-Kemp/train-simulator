package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A vertex in the graph data structure
 * @author 100128483
 */
public class TrackPoint extends Point implements Comparable<TrackPoint> {
    
    private final String CODE;
    private final List<TrackEdge> EDGES = new ArrayList();
    //mainly used for traversal
    private boolean MARK = false; // used for traversal
    
    /**
     * Default constructor (shouldn't be used!)
     */
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

    /**
     * Constructor with all parameters
     * @param source
     * @param target
     * @param code
     * @param x
     * @param y
     */
    public TrackPoint(TrackEdge source, TrackEdge target, String code, int x, int y) {
        super(x,y);
        this.CODE = code;
        this.EDGES.add(0, source); // Ensure source is placed at index 0;
        this.EDGES.add(target);
    }
    
    /**
     * Constructor with all parameters (multiple TrackPoints)
     * @param source
     * @param target
     * @param code
     * @param x
     * @param y
     */
    public TrackPoint(TrackEdge source, List<TrackEdge> target, String code, int x, int y) {
        super(x,y);
        this.CODE = code;
        this.EDGES.add(0, source);
        this.EDGES.addAll(target);
    }
    
    /**
     * Constructor with supplied TrackEdge and new TrackEdge
     * @param p
     * @param code
     * @param x
     * @param y
     * @param length
     * @param gradient
     * @param speedLim
     */
    public TrackPoint(TrackPoint p, String code, int x, int y, double length, double gradient, double speedLim) {
        super(x, y);
        this.CODE = code;
        this.EDGES.add(new TrackEdge(this, p, length, gradient, speedLim));
    }
    
    /**
     * Adds a new edge to the point
     * @param p
     * @param length
     * @param gradient
     * @param speedLim
     * @return whether the addition was successful
     */
    public boolean addEdge(TrackPoint p, double length, double gradient, double speedLim) {
        return this.EDGES.add(new TrackEdge(this, p, length, gradient, speedLim));
    }
    
    /**
     * Add an edge to the point
     * @param e
     * @return whether the addition was successful
     */
    public boolean addEdge(TrackEdge e) {
        return this.EDGES.add(e);
    }
    
    /**
     * @return the code of the point
     */
    public String getCode() {
        return this.CODE;
    }
    
    /**
     * @return list of edges
     */
    public List<TrackEdge> getEdges() {
        return this.EDGES;
    }
    
    /**
     * @param p
     * @return an edge, given the opposing TrackPoint
     */
    public TrackEdge getEdge(TrackPoint p) {
        for(TrackEdge e : this.EDGES)
            if(!e.getTarget(p).equals(p))
                return e;
        return null;
    }
    
    /**
     * @return all neighbouring points from the connected edges
     */
    public List<TrackPoint> getNeighbours() {
        List<TrackPoint> list = new ArrayList<>();
        this.EDGES.forEach(edge -> list.add(edge.getTarget(this)));
        return list;
    }
    
    /**
     * @param edge
     * @return neighbour, given an edge
     */
    public TrackPoint getNeighbour(TrackEdge edge) {
        return edge.getTarget(this);
    }
    
    /**
     * @return whether the point is marked when drawing
     */
    public boolean isMarked() {
        return this.MARK;
    }
    
    /**
     * Mark the point when drawing
     */
    public void mark() {
        this.MARK = true;
    }

    /**
     * Unmark the point before drawing
     */
    public void unmark() {
        this.MARK = false;
    }
    
    /**
     * @param vertexSet
     * @return whether all points are marked
     */
    public static boolean allMarked(List<TrackPoint> vertexSet) {
        return vertexSet.stream().noneMatch(vert -> !vert.isMarked());
    }
    

    /**
     * @param points
     * @return string representation, given points
     */
    
    public static String toString(Set<TrackPoint> points) {
        StringBuilder str = new StringBuilder();
        Iterator<TrackPoint> it = points.iterator();
        it.forEachRemaining(p -> str.append(p.getCode()).append(','));
        return str.toString();
    }

    /**
     * Compare the track point
     * @param o
     * @return ordering alphabetically
     */
    @Override
    public int compareTo(TrackPoint o) {
        return this.CODE.compareTo(o.getCode());
    }
}
