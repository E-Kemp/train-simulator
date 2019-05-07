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
    protected final Service SERVICE_REF;
    protected final TrackPoint SOURCE_POINT;
    protected final TrackPoint TARGET_POINT;
    protected final TrackEdge EDGE;
    protected double DIST;
    
    
    protected AbstractTask(Service service, TrackPoint source, TrackPoint target) {
        this.SERVICE_REF = service;
        this.SOURCE_POINT = source;
        this.TARGET_POINT = target;
        this.EDGE = source.getEdge(target);
        this.DIST = 0;
    }
    
    public TrackEdge getEdge() {
        return this.EDGE;
    }
    
    public TrackPoint getSource() {
        return this.SOURCE_POINT;
    }
    
    public TrackPoint getTarget() {
        return this.TARGET_POINT;
    }
    
    public double getDistance() {
        return this.DIST;
    }
    
    public Service getService() {
        return this.SERVICE_REF;
    }
    
    protected abstract double progress(double speed);
    
    protected abstract double progressSpeed();    
    
}
