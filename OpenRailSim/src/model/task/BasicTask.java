/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.task;

import model.Service;
import model.TrackPoint;

/**
 *
 * @author elliot
 */
public class BasicTask extends AbstractTask {

    /**
     * Basic extention of abstract task
     * @param service
     * @param source
     * @param target
     */
    public BasicTask(Service service, TrackPoint source, TrackPoint target) {
        super(service, source, target);
    }

    @Override
    protected double progress(double speed) {
        return this.DIST += speed;
    }

    @Override
    protected double progressSpeed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
