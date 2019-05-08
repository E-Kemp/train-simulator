/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.task;

import model.Service;
import model.TrackPoint;
import model.TrackEdge;

/**
 *
 * @author elliot
 */
public abstract class AbstractTask {

    /**
     * Reference of the service this task corresponds to
     */
    protected final Service SERVICE_REF;

    /**
     * Source point of the task
     */
    protected final TrackPoint SOURCE_POINT;

    /**
     * Target point of the task
     */
    protected final TrackPoint TARGET_POINT;

    /**
     * Track segment of the task
     */
    protected final TrackEdge EDGE;

    /**
     * Distance travelled in the task
     */
    protected double DIST;
    
    /**
     * Main constructor
     * @param service
     * @param source
     * @param target
     */
    protected AbstractTask(Service service, TrackPoint source, TrackPoint target) {
        this.SERVICE_REF = service;
        this.SOURCE_POINT = source;
        this.TARGET_POINT = target;
        this.EDGE = source.getEdge(target);
        this.DIST = 0;
    }
    
    /**
     * @return edge of the task
     */
    public TrackEdge getEdge() {
        return this.EDGE;
    }
    
    /**
     * @return source point of the task
     */
    public TrackPoint getSource() {
        return this.SOURCE_POINT;
    }
    
    /**
     * @return target point of the task
     */
    public TrackPoint getTarget() {
        return this.TARGET_POINT;
    }
    
    /**
     * @return current distance travelled on the task
     */
    public double getDistance() {
        return this.DIST;
    }
    
    /**
     * @return service reference
     */
    public Service getService() {
        return this.SERVICE_REF;
    }
    
    /**
     * Progress the task using the current speed
     * @param speed
     * @return new distance
     */
    protected abstract double progress(double speed);
    
    /**
     * Progress the task without the current speed
     * @return new distance
     */
    protected abstract double progressSpeed();    
    
}
