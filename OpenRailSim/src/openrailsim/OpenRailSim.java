package openrailsim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

/**
 * One big thing to note about the simulation is that we are operating at berth level, 
 * @author mrp15ndu
 */
public class OpenRailSim extends WeightedMultigraph<Point, TrackSegment> {
    private final HashMap<String, Service> SERVICES;
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    //Default empty constructor
    public OpenRailSim() {
        super(TrackSegment.class);
        this.SERVICES = new HashMap<>();
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
    
    // </editor-fold>
    
    public List<GraphPath> getRoute(Service s) {
        List<GraphPath> l = new ArrayList<>();
        DijkstraShortestPath finder = new DijkstraShortestPath(this);
        for (int i = 1; i < s.getRoute().length; i++) {
            l.add(finder.getPath(s.getRoute()[i-1], s.getRoute()[i]));
        }
        return l;
    }
    
    public List<GraphPath> getRoute(String code) {
        return getRoute(this.SERVICES.get(code));
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
