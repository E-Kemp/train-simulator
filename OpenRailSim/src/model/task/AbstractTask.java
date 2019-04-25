/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.task;

import model.TrackPoint;
import model.TrackEdge;

/**
 *
 * @author elliot
 */
public abstract class TaskInterface {
    protected final TrackPoint SOURCE_POINT;
    protected final TrackPoint TARGET_POINT;
    protected final TrackEdge EDGE;
    protected int DIST;
    
    
    public TaskInterface(TrackPoint source, TrackPoint target, int dist) {
        this(source, target);
        this.DIST = dist;
    }
    
    
    private TaskInterface(TrackPoint source, TrackPoint target) {
        this.SOURCE_POINT = source;
        this.TARGET_POINT = target;
        this.EDGE = source.getEdge(target);
    }
    

    protected abstract int progress();
    
    
}
