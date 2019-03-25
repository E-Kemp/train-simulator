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
        this.SIM = new OpenRailSim();
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
    
    public static TrainType buildTestTrain() {
        try {
            return new TrainType("Test 0", true, 5, 26.0, 0.7, 200, 243, 0);
        } catch(AttributeOutOfRangeException e) {
            System.out.println(e.toString());
            return new TrainType();
        }
    }
}
