package model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jgrapht.GraphPath;
import org.jgrapht.traverse.BreadthFirstIterator;
/**
 * Main controller class that interacts between all classes
 * @author Elliot Jordan Kemp
 */
public class OpenRailSim {
    private final TrackPoint ROOT;
    private int NUM_POINT;
    private final HashMap<String, Service> SERVICES;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    //Default empty constructor
    public OpenRailSim() {
        this.SERVICES = new HashMap<>();
        this.ROOT = new TrackPoint();
        buildTest();
    }
    
//    public OpenRailSim(TrackPoint[] p, TrackSegment[] s, int[][] pairs, Service[] t) {
//        this.SERVICES = new HashMap<>();
//        
//        for(TrackPoint point : p)
//            this.addVertex(point);
//        
//        for (int i = 0; i < s.length; i++)
//            this.addEdge(p[pairs[i][0]], p[pairs[i][1]], s[i]);
//        
//        for(Service service : t)
//            this.SERVICES.put(service.getHeadcode(), service);
//        
//        this.PATH_FINDER = new DijkstraShortestPath(this);
//    }


    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    
    
    // </editor-fold>
    
    public TrackPoint[] getRoute(String code) {
        return this.SERVICES.get(code).getRoute();
    }
    
    public TrackPoint getRoot() {
        return this.ROOT;
    }
    
    
    public Set<TrackPoint> vertexSet() {
        Set<TrackPoint> set = new HashSet<>();
        set.addAll(depthFirstTraversal(this.ROOT));
        return set;
    }
    public Set<TrackPoint> broadSearchTraversal(TrackPoint p, boolean[] visited) {
        Set<TrackPoint> set = new HashSet<>();
        set.add(p);
        for
    }
    
    public int[][] getCoords() {
        int[][] output = new int[this.vertexSet().size()][2];
        
        int i = 0;
        for(TrackPoint p : this.vertexSet()) {
            output[i][0] = (int) p.x;
            output[i][1] = (int) p.y;
            i++;
        }
        return output;
    }
    
    @Override
    public String toString() {
        Set<TrackPoint> pSet = this.vertexSet();
        StringBuilder str = new StringBuilder();
        Iterator<TrackPoint> it = pSet.iterator();
        it.forEachRemaining(p -> str.
                append("Code: ").append(p.getCode()).
                append('\n').append(pointToString(p)).
                append('\n'));
        return str.toString();
    }
    
    public String pathToString(GraphPath path) {
        List<TrackPoint> pList = path.getVertexList();
        StringBuilder str = new StringBuilder("Path: ");
        Iterator<TrackPoint> it = pList.iterator();
        it.forEachRemaining(p ->
            str.append(p.getCode()).append(", "));
        return str.toString();
    }
    
    public String pointToString(TrackPoint p) {
        StringBuilder str = new StringBuilder();
        Iterator<TrackSegment> it = this.edgesOf(p).iterator();
        it.forEachRemaining(t -> str.
                append("Length: ").append(t.getLength()).
                append(" Gradient: ").append(t.getGradient()).
                append('\n'));
        return str.toString();
    }
    
    
    private void buildTest() {
        TrackPoint[] p = new TrackPoint[6];
        p[0] = new TrackPoint("P1", 1,-1);
        p[1] = new TrackPoint("P2", 2, 0);
        p[2] = new TrackPoint("P3", 1, 1);
        p[3] = new TrackPoint("P4", -1, 1);
        p[4] = new TrackPoint("P5", -2, 0);
        p[5] = new TrackPoint("P6", -1, -1);
        this.addVertices(p);
        
        TrackSegment[] t = new TrackSegment[5];
        t[0] = new TrackSegment(15.68, 3);
        t[1] = new TrackSegment(20.84, 5);
        t[2] = new TrackSegment(7, 0);
        t[3] = new TrackSegment(2.7, 3);
        t[4] = new TrackSegment(5.7, 1);
//        this.addEdgeT(p[0], p[5], t[0]);

        for (int i = 0; i < 5; i++) {
            this.addEdgeT(p[i+1], p[i], t[i]);
        }
        
        BreadthFirstIterator<TrackPoint, TrackSegment> it = new BreadthFirstIterator<>(this);
        it.forEachRemaining(q -> {
            System.out.println(q);
        });
        //Iterator<TrackPoint> it = this.vertexSet().iterator();
        
        
    }
    
    public void test(TrackPoint p) {
        System.out.println(this.edgesOf(p));
        //System.out.println("Hello!");
        this.outgoingEdgesOf(p).forEach(q -> {
            System.out.println(q);
            System.out.println(this.getEdgeSource(q));
            System.out.println(this.getEdgeTarget(q));
            test(this.getEdgeTarget(q));
        });
        //this.edgesOf(this.getEdgeSource(t)).forEach(e -> test(e));
    }
    
}
