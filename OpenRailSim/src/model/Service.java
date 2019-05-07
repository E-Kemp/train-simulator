package model;

import model.task.AbstractTask;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import model.task.TaskQueue;

/**
 * A class to represent the service that a train does
 * Consists of one train and a set of destinations (normally stations)
 * @author Elliot Jordan Kemp
 */
public class Service {
    private final String HEADCODE;
    private final List<TrainType> TRAIN;
    private final TaskQueue ROUTE;
    //private final Queue<AbstractTask> TASKS;
    
    private boolean GO = false;
    private int POINT_INDEX_OFFSET;
    private int CUR_POINT_INDEX = 0;
    private double DIST = 0;
    private double SPEED = 0;
    private long START_TIME = System.currentTimeMillis();
    
    public Service(String headcode) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>();
        this.ROUTE = new TaskQueue();
        //this.TASKS = new ArrayDeque<>();
        this.POINT_INDEX_OFFSET = 0;
    }
    
    public Service(String headcode, TrainType[] t, AbstractTask[] p, int offSet) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>(Arrays.asList(t));
        this.ROUTE = new TaskQueue(Arrays.asList(p));
        this.POINT_INDEX_OFFSET = offSet;
    }
    
    public Service(String headcode, TrainType t) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>();
        this.TRAIN.add(t);
        this.ROUTE = new TaskQueue();
        //this.TASKS = new ArrayDeque<>();
        this.POINT_INDEX_OFFSET = 0;
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
    
    public AbstractTask[] getRoute() {
        AbstractTask[] p = new AbstractTask[this.ROUTE.size()];
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
    
    public TrackEdge getCurrentEdge() {
        try {
            if(this.GO)
                return this.ROUTE.get(interIndex()+1).getEdge();
            else
                return this.ROUTE.get(this.ROUTE.size()-1).getEdge();
        } catch(Exception e) { // Fix this laterrrr
            return this.ROUTE.get(0).getEdge();
        }
    }
    
    public TrackPoint getCurrentVert() {
        try {
            return this.ROUTE.get(interIndex()).getSource();
        } catch(Exception e) {
            return this.ROUTE.get(1).getSource();
        }
    }
    
    public long start() {
        this.GO = true;
        return this.START_TIME = System.currentTimeMillis();
    }
    
    public void stop() {
        this.GO = false;
    }
    
    public long getStartTime() {
        return this.START_TIME;
    }
    
    public boolean isRunning() {
        return this.GO;
    }
    
    public int getCurrentMapIndex() {
        return interIndex() + this.POINT_INDEX_OFFSET;
    }
    
    private int interIndex() {
        return this.CUR_POINT_INDEX;
    }
    
    public AbstractTask getCurrentTask() {
        return this.ROUTE.get(CUR_POINT_INDEX);
    }
    
    public double getDistance() {
        return this.DIST;
    }
    
    public double getStoppingDistance() { // metres
        double ratio = this.SPEED / this.getLoco().getDec();
        return 0.5 * this.SPEED * ratio;
    }
    
    public double getAngle() {
        if(interIndex()+1 >= this.ROUTE.size())
            return 0;
        return Math.atan2(
                (this.ROUTE.get(interIndex()).getTarget().y-this.ROUTE.get(interIndex()).getSource().y),
                (this.ROUTE.get(interIndex()).getTarget().x-this.ROUTE.get(interIndex()).getSource().x)
        ) - (Math.PI/2);
    }
    
    public double progress(double rate) {
        if(GO) {
            
            
            //Firstly set the speed of the service
            if(this.SPEED + (this.TRAIN.get(0).getAcc() * 3.6) >= this.getCurrentEdge().getSpeedLimit())
                this.SPEED = this.getCurrentEdge().getSpeedLimit();
            else
                this.SPEED += (this.TRAIN.get(0).getAcc() * 3.6) * rate;
            
            // Add the distance
            this.DIST += ((this.SPEED/3600) * rate); // converted to km/s
            if(this.DIST >= this.getCurrentEdge().getLength()) {
                
                this.DIST -= this.getCurrentEdge().getLength();
                this.CUR_POINT_INDEX++;
                
                if(interIndex() == this.ROUTE.size()) {
                    this.CUR_POINT_INDEX = this.ROUTE.size()-1;
                    this.DIST = this.getCurrentEdge().getLength();
                    this.stop();
                }
                //Add feature for if this even goes beyond the next point . . .
            }
            return this.DIST;
        }
        return 0;
    }
    
    
    public boolean addRouteTask(AbstractTask p, Integer offSet) {
        if(offSet != null)
            this.POINT_INDEX_OFFSET = offSet;        
        return this.ROUTE.add(p);    
    }
    
    public boolean addRouteTasks(List<AbstractTask> p, Integer offSet) {
        if(offSet != null)
            this.POINT_INDEX_OFFSET = offSet;  
        return this.ROUTE.addAll(p);
    }
    
}
