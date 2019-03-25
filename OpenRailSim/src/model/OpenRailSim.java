package model;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/**
 * Main controller class that interacts between all classes
 * JGraphT ditched because of the nuances and problems it causes
 * @author Elliot Jordan Kemp
 */
public class OpenRailSim {
    private final List<TrackPoint> VERT_LIST;
    private final HashMap<String, Service> SERVICES;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    //Default empty constructor
    public OpenRailSim() {
        this.SERVICES = new HashMap<>();
        this.VERT_LIST = new ArrayList<>();
        buildTest();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Get route of a service headcode
     * @param code
     * @return array of track points
     */
    public TrackPoint[] getRoute(String code) {
        return this.SERVICES.get(code).getRoute();
    }
    
    /**
     * Get the TrackPoints of a set of codes
     * @param codes
     * @return array of track points
     */
    public TrackPoint[] getRoute(String[] codes) {
        TrackPoint[] pointArr = new TrackPoint[codes.length];
        for (int i = 0; i < codes.length; i++)
            pointArr[i] = this.getVert(codes[i]);
        return pointArr;
    }
    
    /**
     * Get the 'root' of the map, used for traversal
     * @return root of the map 
     */
    public TrackPoint getRoot() {
        return this.VERT_LIST.get(0); // Assume the first index is the root node
    }
    
    public TrackPoint getVert(String code) {
        for(int i = 0; i < this.VERT_LIST.size(); i++) { // Don't use functional operator
            if(this.VERT_LIST.get(i).getCode().equalsIgnoreCase(code))
                return this.VERT_LIST.get(i);
        }
        return null;
    }
    
    public List<TrackPoint> vertexSet() {
        return this.VERT_LIST;
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
    
    public boolean addVertex(TrackPoint p1, String code, int x, int y, double length, double gradient) {
        TrackPoint p2 = new TrackPoint(p1, code, x, y, length, gradient);
        p1.addEdge(p2, length, gradient);
        return this.VERT_LIST.add(p2);
    }
    
    public boolean addVertex(TrackPoint p1, String code, double angle, double length, double gradient) {
        angle = Math.toRadians(angle);
        int x = p1.x + (int) (Math.sin(angle) * length);
        int y = p1.y + (int) (Math.cos(angle) * length);
        TrackPoint p2 = new TrackPoint(p1, code, x, y, length, gradient);
        p1.addEdge(p2, length, gradient);
        return this.VERT_LIST.add(p2);
    }
    
    public void addEdge(TrackPoint p1, TrackPoint p2, double length, double gradient) {
        TrackSegment e = new TrackSegment(p1, p2, length, gradient);
        p1.addEdge(e);
        p2.addEdge(e);
    }
    
    // </editor-fold>
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Iterator<TrackPoint> it = this.VERT_LIST.iterator();
        it.forEachRemaining(p -> str.
                append("Code: ").append(p.getCode()).
                append('\n').append(pointToString(p)).
                append('\n'));
        return str.toString();
    }
    
    public String pointToString(TrackPoint p) {
        StringBuilder str = new StringBuilder();
        Iterator<TrackSegment> it = p.getEdges().iterator();
        it.forEachRemaining(t -> str.
                append("Length: ").append(t.getLength()).
                append(" Gradient: ").append(t.getGradient()).
                append('\n').
                append(t.getTarget(p).getCode()));
        return str.toString();
    }
    
    public List<Line2D> getMap() {
        List<Line2D> list = new ArrayList<>();
        this.VERT_LIST.forEach(p -> p.unmark()); //Make sure all vertices are unmarked
        
        this.VERT_LIST.forEach(p -> {
            System.out.printf("%s %d, %d:\n", "Connections for", p.x, p.y);
            for(TrackSegment e : p.getEdges()) {
                TrackPoint p2 = e.getTarget(p);
                System.out.printf("%d, %d\n",p2.x, p2.y);
                if(!p2.isMarked())
                    list.add(new Line2D.Double(p, p2));
            }
            p.mark();
        });
        
        return list;
    }
    
//    private void buildTest() {
//        this.VERT_LIST.add(new TrackPoint("P0", 0, 200));
//        this.addVertex(VERT_LIST.get(0), "P1", -100, 0, 100, 0);
//        this.addVertex(VERT_LIST.get(1), "P2", 100, 0, 100, 0);
//        this.addEdge(this.getVert("P0"), this.getVert("P2"), 200, 0);
//        this.addVertex(VERT_LIST.get(2), "P3", 100, -100, 100, 0);
//        this.addVertex(VERT_LIST.get(3), "P4", -100, -100, 100, 0);
//        this.addEdge(this.getVert("P1"), this.getVert("P4"), 100, 0);
//        this.addEdge(this.getVert("P1"), this.getVert("P3"), 100, 0);
//    }
    
    private void buildTest() {
        this.VERT_LIST.add(new TrackPoint("P0", -300, -200));
        this.addVertex(this.getVert("P0"), "P1", -245, -185, 100, 0);
        this.addVertex(this.getVert("P1"), "P2", -230, -150, 100, 0);
        this.addVertex(this.getVert("P2"), "P3", -190, -110, 100, 0);
        this.addVertex(this.getVert("P3"), "P4", -50, -50, 100, 0);
        this.addVertex(this.getVert("P4"), "P5", 20, 0, 100, 0);
        this.addVertex(this.getVert("P5"), "P6", 100, 75, 100, 0);
        this.addVertex(this.getVert("P6"), "P7", 160, 100, 100, 0);
        this.addVertex(this.getVert("P7"), "P8", 210, 120, 100, 0);
        this.addVertex(this.getVert("P8"), "P9", 245, 110, 100, 0);
        this.addVertex(this.getVert("P9"), "P10", 275, 90, 100, 0);
        this.addVertex(this.getVert("P10"), "P11", 300, 110, 100, 0);
        
        this.addVertex(this.getVert("P4"), "P12", 110, -20, 100, 0);
        this.addVertex(this.getVert("P5"), "P13", 110, 100, 0); // by angles!
    }   
    
    public void test(TrackPoint p) {
        
    }
    
}
