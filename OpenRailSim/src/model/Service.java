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
    
    public double getSpeed() {
        return this.SPEED;
    }
    
    public double getSpeed(long time) {
        return this.SPEED += (this.TRAIN.get(0).getAcc()*(time/1000));
    }
    
    public long start() {
        return this.START_TIME = System.currentTimeMillis();
    }
    
    public long getStartTime() {
        return this.START_TIME;
    }
    

    public double trainWeight() {
        double weight = 0;
        for(TrainType t : this.TRAIN)
            weight += t.getWeight();
        return weight;
    }
    
}
