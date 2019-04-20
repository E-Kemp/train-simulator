package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class to represent the service that a train does
 * Consists of one train and a set of destinations (normally stations)
 * @author Elliot Jordan Kemp
 */
public class Service {
    private final String HEADCODE;
    private final List<TrainType> TRAIN;
    private final List<TrackPoint> ROUTE;
    
    private boolean GO = false;
    private int CUR_POINT_INDEX = 0;
    private double DIST = 0;
    private double SPEED = 0;
    private long START_TIME = System.currentTimeMillis();
    
    public Service(String headcode) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>();
        this.ROUTE = new ArrayList<>();
    }
    
    public Service(String headcode, TrainType[] t, TrackPoint[] p) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>(Arrays.asList(t));
        this.ROUTE = new ArrayList<>(Arrays.asList(p));
    }
    
    public Service(String headcode, TrainType t) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>();
        this.TRAIN.add(t);
        this.ROUTE = new ArrayList<>();
    }
    
    public String getHeadcode() {
        return this.HEADCODE;
    }
    
    public TrainType[] getTrain() { 
        TrainType[] t = new TrainType[this.TRAIN.size()];
        return this.TRAIN.toArray(t);
    }
    
    public TrainType getLoco() {
        return this.TRAIN.get(0);
    }
    
    public TrackPoint[] getRoute() {
        TrackPoint[] p = new TrackPoint[this.ROUTE.size()];
        return this.ROUTE.toArray(p);
    }
    


    public double trainWeight() {
        double weight = 0;
        for(TrainType t : this.TRAIN)
            weight += t.getWeight();
        return weight;
    }
    
    
    
    public double getSpeed() {
        return this.SPEED;
    }
    
    public double getSpeed(long time) {
        return this.SPEED += (this.TRAIN.get(0).getAcc()*(time/1000));
    }
    
    public TrackSegment getCurrentEdge() {
        try {
            return this.ROUTE.get(this.CUR_POINT_INDEX).getEdge(this.ROUTE.get(CUR_POINT_INDEX+1));
        } catch(Exception e) {
            return null;
        }
    }
    
    public TrackPoint getCurrentVert() {
        try {
            return this.ROUTE.get(this.CUR_POINT_INDEX);
        } catch(Exception e) {
            return this.ROUTE.get(1);
        }
    }
    
    
    public long start() {
        this.GO = true;
        return this.START_TIME = System.currentTimeMillis();
    }
    
    public long getStartTime() {
        return this.START_TIME;
    }
    
    public boolean isRunning() {
        return this.GO;
    }
    
    public int getCurrentIndex() {
        return this.CUR_POINT_INDEX;
    }
    
    public double progress(double rate) {
        //if(GO) {
            //Firstly set the speed of the service
            if(this.SPEED + (this.TRAIN.get(0).getAcc() * 3.6) >= this.getCurrentEdge().getSpeedLimit())
                this.SPEED = this.getCurrentEdge().getSpeedLimit() * rate;
            else
                this.SPEED += (this.TRAIN.get(0).getAcc() * 3.6);
            
            // Add the distance
            this.DIST += (this.SPEED/3600) * rate; // converted to km/s
            if(this.DIST > this.getCurrentEdge().getLength()) {
                this.DIST -= this.getCurrentEdge().getLength();
                this.CUR_POINT_INDEX++;
                //Add feature for if this even goes beyond the next point . . .
            }
            return this.DIST;
        //}
        //return 0;
    }
    
    
    public boolean addRoutePoint(TrackPoint p) {
        return this.ROUTE.add(p);
    }
    
    public boolean addRoutePoints(List<TrackPoint> p) {
        return this.ROUTE.addAll(p);
    }
    
}
