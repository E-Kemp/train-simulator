package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.task.AbstractTask;
import model.task.TaskQueue;

/**
 * A class to represent the service that a train performs
 * Consists of one train and a set of destinations (normally stations)
 * @author 100128483
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
    
    /**
     * Constructor with just a headcode
     * @param headcode
     */
    public Service(String headcode) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>();
        this.ROUTE = new TaskQueue();
        //this.TASKS = new ArrayDeque<>();
        this.POINT_INDEX_OFFSET = 0;
    }
    
    /**
     * Constructor with full parameter set
     * @param headcode
     * @param t
     * @param p
     * @param offSet
     */
    public Service(String headcode, TrainType[] t, AbstractTask[] p, int offSet) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>(Arrays.asList(t));
        this.ROUTE = new TaskQueue(Arrays.asList(p));
        this.POINT_INDEX_OFFSET = offSet;
    }
    
    /**
     * Constructor with just headcode and TrainType
     * @param headcode
     * @param t
     */
    public Service(String headcode, TrainType t) {
        this.HEADCODE = headcode;
        this.TRAIN = new ArrayList<>();
        this.TRAIN.add(t);
        this.ROUTE = new TaskQueue();
        //this.TASKS = new ArrayDeque<>();
        this.POINT_INDEX_OFFSET = 0;
    }
    
    /**
     * @return headcode
     */
    public String getHeadcode() {
        return this.HEADCODE;
    }
    
    /** 
     * @return array of TrainTypes
     */
    public TrainType[] getTrain() { 
        TrainType[] t = new TrainType[this.TRAIN.size()];
        return this.TRAIN.toArray(t);
    }
    
    /**
     * @return first in the list of TrainTypes
     */
    public TrainType getLoco() {
        return this.TRAIN.get(0);
    }
    
    /**
     * @return array of all tasks
     */
    public AbstractTask[] getRoute() {
        AbstractTask[] p = new AbstractTask[this.ROUTE.size()];
        return this.ROUTE.toArray(p);
    }
    
    /**
     * @return weight of the train
     */
    public double trainWeight() {
        double weight = 0;
        for(TrainType t : this.TRAIN)
            weight += t.getWeight();
        return weight;
    }
    
    /**
     * @return speed of the service
     */
    public double getSpeed() {
        return this.SPEED;
    }
    
    /**
     * @param time
     * @return speed after a set time
     */
    public double getSpeed(long time) {
        return this.SPEED += (this.TRAIN.get(0).getAcc()*(time/1000));
    }
    
    /**
     * @return current edge that the train is on
     */
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
    
    /**
     * @return current vertex that the train is projecting from
     */
    public TrackPoint getCurrentVert() {
        try {
            return this.ROUTE.get(interIndex()).getSource();
        } catch(Exception e) {
            return this.ROUTE.get(1).getSource();
        }
    }
    
    /**
     * Start the service
     * @return start time
     */
    public long start() {
        this.GO = true;
        return this.START_TIME = System.currentTimeMillis();
    }
    
    /**
     * Stop the service
     */
    public void stop() {
        this.GO = false;
    }
    
    /**
     * @return start time
     */
    public long getStartTime() {
        return this.START_TIME;
    }
    
    /**
     * @return whether the service is running
     */
    public boolean isRunning() {
        return this.GO;
    }
    
    /**
     * @return current map index
     */
    public int getCurrentMapIndex() {
        return interIndex() + this.POINT_INDEX_OFFSET;
    }
    
    private int interIndex() {
        return this.CUR_POINT_INDEX;
    }
    
    /**
     * @return current task of the service
     */
    public AbstractTask getCurrentTask() {
        return this.ROUTE.get(CUR_POINT_INDEX);
    }
    
    /**
     * @return distance from the current point
     */
    public double getDistance() {
        return this.DIST;
    }
    
    /**
     * @return stopping distance of the service under normal breaking conditions
     */
    public double getStoppingDistance() { // metres
        double ratio = this.SPEED / this.getLoco().getDec();
        return 0.5 * this.SPEED * ratio;
    }
    
    /**
     * @return angle of the train in accordance to the map
     */
    public double getAngle() {
        if(interIndex()+1 >= this.ROUTE.size())
            return 0;
        return Math.atan2(
                (this.ROUTE.get(interIndex()).getTarget().y-this.ROUTE.get(interIndex()).getSource().y),
                (this.ROUTE.get(interIndex()).getTarget().x-this.ROUTE.get(interIndex()).getSource().x)
        ) - (Math.PI/2);
    }
    
    /**
     * Progress the service, scaled by the rate provided
     * @param rate
     * @return progressed distance of the train
     */
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
    
    /**
     * Add a task to the route
     * @param p
     * @param offSet
     * @return whether the addition was successful
     */
    public boolean addRouteTask(AbstractTask p, Integer offSet) {
        if(offSet != null)
            this.POINT_INDEX_OFFSET = offSet;        
        return this.ROUTE.add(p);    
    }
    
    /**
     * Add a list of tasks to the route
     * @param p
     * @param offSet
     * @return whether the additions were successful
     */
    public boolean addRouteTasks(List<AbstractTask> p, Integer offSet) {
        if(offSet != null)
            this.POINT_INDEX_OFFSET = offSet;  
        return this.ROUTE.addAll(p);
    }
    
}
