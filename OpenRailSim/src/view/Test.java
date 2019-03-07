package view;

import model.AttributeOutOfRangeException;
import model.OpenRailSim;
import model.TrackPoint;
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
        this.SIM = buildTest();
    }
    
    public static void main(String[] args) throws InterruptedException {
        Test t = new Test();
        t.test1();
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
    
    
    public static OpenRailSim buildTest() {
        OpenRailSim sim = new OpenRailSim();
        
        TrackPoint[] p = new TrackPoint[6];
        p[0] = new TrackPoint("P1", 1,-1);
        p[1] = new TrackPoint("P2", 2, 0);
        p[2] = new TrackPoint("P3", 1, 1);
        p[3] = new TrackPoint("P4", -1, 1);
        p[4] = new TrackPoint("P5", -2, 0);
        p[5] = new TrackPoint("P6", -1, -1);
        sim.addVertices(p);
        
        TrackSegment[] t = new TrackSegment[6];
        t[0] = new TrackSegment(15.68, 3);
        t[1] = new TrackSegment(20.84, 5);
        t[2] = new TrackSegment(7, 0);
        t[3] = new TrackSegment(2.7, 3);
        t[4] = new TrackSegment(5.7, 1);
        t[5] = new TrackSegment(9.5, 2.3);
        sim.addEdgeT(p[0], p[5], t[0]);
        for (int i = 1; i < 6; i++) {
            sim.addEdgeT(p[i], p[i-1], t[i]);
        }
        
        System.out.println(sim.toString());
        return sim;
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
