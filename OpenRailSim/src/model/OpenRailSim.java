package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

/**
 * Main controller class that interacts between all classes
 * @author Elliot Jordan Kemp
 */
public class OpenRailSim extends WeightedMultigraph<Point, TrackSegment> {
    private final HashMap<String, Service> SERVICES;
    private DijkstraShortestPath PATH_FINDER;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    //Default empty constructor
    public OpenRailSim() {
        super(TrackSegment.class);
        this.SERVICES = new HashMap<>();
        this.PATH_FINDER = new DijkstraShortestPath(this);
    }
    
    public OpenRailSim(Point[] p, TrackSegment[] s, int[][] pairs, Service[] t) {
        super(TrackSegment.class);

        this.SERVICES = new HashMap<>();
        
        for(Point point : p)
            this.addVertex(point);
        
        for (int i = 0; i < s.length; i++)
            this.addEdge(p[pairs[i][0]], p[pairs[i][1]], s[i]);
        
        for(Service service : t)
            this.SERVICES.put(service.getHeadcode(), service);
        
        this.PATH_FINDER = new DijkstraShortestPath(this);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public Point getPoint(String code) {
        for(Point p : this.vertexSet())
            if(p.isPoint(code))
                return p;
        return null;
    }
    
    public TrackSegment addEdge(Point p1, Point p2, double length) {
        TrackSegment t = this.addEdge(p1, p2);
        this.setEdgeWeight(t, length);
        return t;
    }
        
    public boolean addEdgeT(Point p1, Point p2, TrackSegment s) {
        if(this.containsVertex(p1) && this.containsVertex(p2)) {
            this.addEdge(p2, p1, s);
            this.setEdgeWeight(s, s.getLength());
            return true;
        }
        else return false;
    }
        
    public boolean addVertices(Point[] v) {
        for(Point p : v)
            if(!this.addVertex(p))
                return false;
        return true;
    }
    
    public void reloadPathFinder() {
        this.PATH_FINDER = new DijkstraShortestPath(this);
    }
    
    // </editor-fold>
    
    public Point[] getRoute(String code) {
        return this.SERVICES.get(code).getRoute();
    }
    
    @Override
    public String toString() {
        Set<Point> pSet = this.vertexSet();
        StringBuilder str = new StringBuilder();
        Iterator<Point> it = pSet.iterator();
        it.forEachRemaining(p -> str.
                append("Code: ").append(p.getCode()).
                append('\n').append(pointToString(p)).
                append('\n'));
        return str.toString();
    }
    
    public String pathToString(GraphPath path) {
        List<Point> pList = path.getVertexList();
        StringBuilder str = new StringBuilder("Path: ");
        Iterator<Point> it = pList.iterator();
        it.forEachRemaining(p ->
            str.append(p.getCode()).append(", "));
        return str.toString();
    }
    
    public String pointToString(Point p) {
        StringBuilder str = new StringBuilder();
        Iterator<TrackSegment> it = this.edgesOf(p).iterator();
        it.forEachRemaining(t -> str.
                append("Length: ").append(t.getLength()).
                append(" Gradient: ").append(t.getGradient()).
                append('\n'));
        return str.toString();
    }
    
}
