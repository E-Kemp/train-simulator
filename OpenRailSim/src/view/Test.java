package view;

import model.AttributeOutOfRangeException;
import model.OpenRailSim;
import model.Point;
import model.Service;
import model.TrackSegment;
import model.TrainType;


/**
 * Test class
 * @author Elliot Jordan Kemp
 */
public class Test {
    protected final OpenRailSim SIM;
    
    Test() {
        this.SIM = new OpenRailSim();
    }
    
    public static void main(String[] args) throws InterruptedException {
        Test t = new Test();
        //t.buildTest();
        //t.test1();
        
        OpenRailSimGUIOld gui = new OpenRailSimGUIOld();
        
        gui.setVisible(true);
    }
    
    public void test1() {
        Service s = new Service("TEST", buildTestTrain());
        s.start();
//        System.out.println(t.SIM.pathToString(
//                t.SIM.getRoute(s).get(0)));
        
        for (int i = 0; i < 10; i++) {
            s.getSpeed(10000);
            System.out.printf("%.2f kph\n", s.getSpeed());
        }        
    }
    
    public void buildTest() {
        Point[] p = new Point[6];
        p[0] = new Point("P1");
        p[1] = new Point("P2");
        p[2] = new Point("P3");
        p[3] = new Point("P4");
        p[4] = new Point("P5");
        p[5] = new Point("P6");
        this.SIM.addVertices(p);
        
        TrackSegment[] t = new TrackSegment[6];
        t[0] = new TrackSegment(15.68, 3);
        t[1] = new TrackSegment(20.84, 5);
        t[2] = new TrackSegment(7, 0);
        t[3] = new TrackSegment(2.7, 3);
        t[4] = new TrackSegment(5.7, 1);
        t[5] = new TrackSegment(9.5, 2.3);
        this.SIM.addEdgeT(p[0], p[5], t[0]);
        for (int i = 1; i < 6; i++) {
            this.SIM.addEdgeT(p[i], p[i-1], t[i]);
        }
        
        System.out.println(SIM.toString());
    }
    
    public static TrainType buildTestTrain() {
        try {
            return new TrainType("Test 0", true, 5, 26.0, 0.7, 200, 243, 0);
        } catch(AttributeOutOfRangeException e) {
            System.out.println(e.toString());
            return new TrainType();
        }
    }
}
