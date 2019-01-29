package openrailsim;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.*;

public class OpenRailSim {

    private final Graph<Point, TrackSegment> GRAPH;
    private final Map<String, TrainType> TRAIN_TYPE;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    //Default empty constructor
    public OpenRailSim() {
        this.GRAPH = new DirectedMultigraph<>(TrackSegment.class);
        this.TRAIN_TYPE = new HashMap<>();
    }
    
    public OpenRailSim(Point[] p, TrackSegment[] s, int[][] pairs, TrainType[] t) {
        this.GRAPH = new DirectedMultigraph<>(TrackSegment.class);
        for(Point point : p)
            this.GRAPH.addVertex(point);
        
        for (int i = 0; i < s.length; i++)
            this.GRAPH.addEdge(p[pairs[i][0]], p[pairs[i][1]], s[i]);

        this.TRAIN_TYPE = new HashMap<>();
        
        for(TrainType type : t)
            this.TRAIN_TYPE.put(type.getTrnClass(), type);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public Set<TrackSegment> getTrackSegments() {
        return this.GRAPH.edgeSet();
    }
    
    public Set<Point> getPoints() {
        return this.GRAPH.vertexSet();
    }
    
    public TrackSegment addTrackSegment(Point p1, Point p2) {
        return this.GRAPH.addEdge(p1, p2);
    }
    
    public boolean addPoint(Point v) {
        return this.GRAPH.addVertex(v);
    }
    
    // </editor-fold>
    
    
    
    
    
    
    
    
}
