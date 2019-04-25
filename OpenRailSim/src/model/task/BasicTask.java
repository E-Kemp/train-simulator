/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.task;

import model.TrackPoint;

/**
 *
 * @author elliot
 */
public class BasicTask extends AbstractTask {

    public BasicTask(TrackPoint source, TrackPoint target) {
        super(source, target);
    }

    @Override
    protected int progress() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
