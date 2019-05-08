package model;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import model.task.*;

/**
 * Main controller class that interacts between all classes
 * 
 * @author 100128483
 */
public class OpenRailSim extends Thread {

    /**
     * Class to add extra methods to the ArrayList
     */
    public class PointList extends ArrayList<TrackPoint> {
        /**
         * Alternative to using the index
         * @param code
         * @return TrackPoint
         */
        public TrackPoint get(String code) {            
            for (TrackPoint point : this) {
                if(point.getCode().equals(code))
                    return point;
            }
            return null;
        }
        
        /**
         * Alternative to using the object itself
         * @param code
         * @return index
         */
        public int indexOf(String code) {
            for(int i = 0; i < this.size(); i++) { // Don't use functional operator
                if(this.get(i).getCode().equalsIgnoreCase(code))
                    return i;
            }
            return -1;
        }
        
        /**
         * Overriden subList method
         * @param fromIndex
         * @param toIndex
         * @return
         */
        @Override
        public PointList subList(int fromIndex, int toIndex) {
            PointList p = new PointList();
            for (int i = fromIndex; i < toIndex; i++) {
                p.add(this.get(i));
            }
            return p;
        }
    }
    
    /**
     * Run method to run the simulation thread
     */
    @Override
    public void run() {
        this.SERVICES.forEach((str, s) -> s.start());
        while(true) {
            this.SERVICES.forEach((str, s) -> {
                if(s.isRunning())
                    s.progress(RATE);

//                System.out.printf("%s: %d %10s: %4f %10s: %4f\n", 
//                        "Index", s.getCurrentIndex(), 
//                        "Dist", s.getDistance(),
//                        "Speed", s.getSpeed());

                //More code for running the fucking sim

            });
            try {
                Thread.sleep(TICK_RATE); // Runs every 10th of a second
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // very important
                break;
            }
        }
    }

    private final int TICK_RATE = 10; // 100ms
    
    private final PointList VERT_LIST;
    private final LinkedHashMap<String, Service> SERVICES;

    /**
     * Rate of the simulation
     */
    public double RATE = 0.1;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    

    /**
     * Default empty constructor
     */
    public OpenRailSim() {
        this.SERVICES = new LinkedHashMap<>();
        this.VERT_LIST = new PointList();
        buildTest();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * @return all the keys in the set of services
     */
    public Set getServiceList() {
        return this.SERVICES.keySet();
    }
    
    /** 
     * @return a LinkedHashMap of services
     */
    public LinkedHashMap<String, Service> cacheServices() {
        return this.SERVICES;
    }
    
    /**
     * Get tasks of a service headcode
     * @param code
     * @return array of track points
     */
    public AbstractTask[] getRoute(String code) {
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
     * Get the 'root' of the map, used for drawing
     * @return root of the map 
     */
    public TrackPoint getRoot() {
        return this.VERT_LIST.get(0); // Assume the first index is the root node
    }
    
    /**
     * Gets a specific vertex given a code
     * @param code
     * @return the vertex
     */
    public TrackPoint getVert(String code) {
        return this.VERT_LIST.get(code);
    }
    
    /**
     * Gets a specific vertex given the index in the array of vertices
     * @param index
     * @return the vertex
     */
    public TrackPoint getVert(int index) {
        return this.VERT_LIST.get(index);
    }
    
    /**
     * Used for Dijkstra's path finding algorithm (UNFINISHED) and optimisation of
     * getting vertices from the graph
     * @param code
     * @return index of the specified code
     */
    public int getVertIndex(String code) {
        return this.VERT_LIST.indexOf(code);
    }
    
    /**
     * @return list of vertices
     */
    public PointList vertexSet() {
        return this.VERT_LIST;
    }
    
    /**
     * @return all coordinates in a 2D array
     */
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
    
    /**
     * Add a vertex given direct positioning
     * @param p1
     * @param code
     * @param x
     * @param y
     * @param length
     * @param gradient
     * @param speedLim
     * @return whether the addition was successful
     */
    public boolean addVertex(TrackPoint p1, String code, int x, int y, double length, double gradient, double speedLim) {
        TrackPoint p2 = new TrackPoint(p1, code, x, y, length, gradient, speedLim);
        p1.addEdge(p2, length, gradient, speedLim);
        return this.VERT_LIST.add(p2);
    }
    
    /**
     * Add a vertex given an angle and length
     * @param p1
     * @param code
     * @param angle
     * @param screenLength
     * @param length
     * @param gradient
     * @param speedLim
     * @return whether the addition was successful
     */
    public boolean addVertex(TrackPoint p1, String code, double angle, int screenLength, double length, double gradient, double speedLim) {
        angle = Math.toRadians(angle);
        int x = p1.x + (int) (Math.sin(angle) * screenLength);
        int y = p1.y + (int) (Math.cos(angle) * screenLength);
        TrackPoint p2 = new TrackPoint(p1, code, x, y, length, gradient, speedLim);
        p1.addEdge(p2, length, gradient, speedLim);
        return this.VERT_LIST.add(p2);
    }
    
    /**
     * Add an edge given two TrackPoints (joining TrackPoints)
     * @param p1
     * @param p2
     * @param length
     * @param gradient
     * @param speedLim
     */
    public void addEdge(TrackPoint p1, TrackPoint p2, double length, double gradient, double speedLim) {
        TrackEdge e = new TrackEdge(p1, p2, length, gradient, speedLim);
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
    
    /**
     * @param p
     * @return the information of a specific point
     */
    public String pointToString(TrackPoint p) {
        StringBuilder str = new StringBuilder();
        Iterator<TrackEdge> it = p.getEdges().iterator();
        it.forEachRemaining(t -> str.
                append("Length: ").append(t.getLength()).
                append(" Gradient: ").append(t.getGradient()).
                append('\n').
                append(t.getTarget(p).getCode()));
        return str.toString();
    }
    
    /**
     * @return map of the tracks for drawing
     */
    public List<Line2D> getMap() {
        List<Line2D> list = new ArrayList<>();
        this.VERT_LIST.forEach(p -> p.unmark()); //Make sure all vertices are unmarked
        
        this.VERT_LIST.forEach(p -> {
            for(TrackEdge e : p.getEdges()) {
                TrackPoint p2 = e.getTarget(p);
                if(!p2.isMarked())
                    list.add(new Line2D.Double(p, p2));
            }
            p.mark();
        });
        
        return list;
    }
    
    /**
     * Get the service current index on the map
     * @param s
     * @return map index
     */
    public int getCurrentMapIndex(Service s) {
        return this.getVertIndex(s.getRoute()[0].getSource().getCode()) + s.getCurrentMapIndex();
    }
    
    /**
     * Sets the rate from the slider in SwingFrame
     * @param newSpeed
     */
    public void setRate(double newSpeed) {
        this.RATE = newSpeed/1000;
        System.out.println(this.RATE);
    }
    
    private void buildTest() {
//        this.VERT_LIST.add(new TrackPoint("P0", -300, -200)); // Route!!!
//        this.addVertex(this.getVert("P0"), "P1", 45.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P1"), "P2", 60.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P2"), "P3", 70.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P3"), "P4", 80.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P4"), "P5", 85.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P5"), "P6", 80.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P6"), "P7", 70.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P7"), "P8", 65.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P8"), "P9", 55.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P9"), "P10", 45.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("P10"), "P11", 35.0, 50, 10, 0, 100);
//        
//        this.VERT_LIST.add(new TrackPoint("Q0", -320, -180)); // Route!!!
//        this.addVertex(this.getVert("Q0"), "Q1", 45.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q1"), "Q2", 60.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q2"), "Q3", 70.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q3"), "Q4", 80.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q4"), "Q5", 85.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q5"), "Q6", 80.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q6"), "Q7", 70.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q7"), "Q8", 65.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q8"), "Q9", 55.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q9"), "Q10", 45.0, 50, 10, 0, 100);
//        this.addVertex(this.getVert("Q10"), "Q11", 35.0, 50, 10, 0, 100);
        
        
        
        
        this.VERT_LIST.add(new TrackPoint("P0", -300, -200)); // Route!!!
        this.addVertex(this.getVert("P0"), "P1", -245, -185, 10, 0, 120);
        this.addVertex(this.getVert("P1"), "P2", -230, -150, 10, 0, 120);
        this.addVertex(this.getVert("P2"), "P3", -190, -110, 10, 0, 120);
        this.addVertex(this.getVert("P3"), "P4", -50, -50, 10, 0, 120);
        this.addVertex(this.getVert("P4"), "P5", 20, 0, 10, 0, 120);
        this.addVertex(this.getVert("P5"), "P6", 100, 75, 10, 0, 120);
        this.addVertex(this.getVert("P6"), "P7", 160, 100, 10, 0, 120);
        this.addVertex(this.getVert("P7"), "P8", 210, 120, 10, 0, 120);
        this.addVertex(this.getVert("P8"), "P9", 245, 110, 10, 0, 120);
        this.addVertex(this.getVert("P9"), "P10", 275, 90, 10, 0, 120);
        this.addVertex(this.getVert("P10"), "P11", 300, 110, 10, 0, 120);
        
        this.addVertex(this.getVert("P4"), "P12", 150, -80, 10, 0, 120);
        this.addVertex(this.getVert("P5"), "P13", 110.0, 100, 10, 0, 120); // by angles!
        
        this.SERVICES.put("TEST1", new Service("TEST1", TrainType.test()));
        this.SERVICES.put("TEST2", new Service("TEST2", TrainType.test()));
        
        TaskQueue q1 = new TaskQueue();
        TaskQueue q2 = new TaskQueue();
        for (int i = 0; i < this.VERT_LIST.size()-3; i++) {
            q1.add(new BasicTask(this.SERVICES.get("TEST1"), this.getVert(i), this.getVert(i+1)));
            if(i > 3)
                q2.add(new BasicTask(this.SERVICES.get("TEST2"), this.getVert(i), this.getVert(i+1)));
            //q2.add(new BasicTask(this.SERVICES.get("TEST2"), this.getVert(this.VERT_LIST.size()-i-1), this.getVert(this.VERT_LIST.size()-i-2)));
                
        }
        
        
        this.SERVICES.get("TEST2").addRouteTasks(q2, 3);
        
        
        this.SERVICES.get("TEST1").addRouteTasks(q1, 0);
        
        TrackPoint tp1, tp0 = this.VERT_LIST.get(0);
        for(int i = 1; i < this.VERT_LIST.size(); i++) {
            tp1 = this.VERT_LIST.get(i);
            tp0 = this.VERT_LIST.get(i-1);
        }
    }   
    
}
