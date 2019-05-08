package model.task;

import model.Service;
import model.TrackPoint;

/**
 * Task either in progress to slow down or slow down and stop the train
 * @author 100128483
 */
public class StoppingTask extends AbstractTask {
    private final double STOP_POINT;
    
    /**
     * Full constructor
     * @param service
     * @param source
     * @param end
     */
    public StoppingTask(Service service, TrackPoint source, TrackPoint end) {
        super(service, source, end);
        this.STOP_POINT = this.EDGE.getLength();
    }
    
    /**
     * Full constructor with custom end point
     * @param service
     * @param source
     * @param end
     * @param stopPoint
     */
    public StoppingTask(Service service, TrackPoint source, TrackPoint end, double stopPoint) {
        super(service, source, end);
        this.STOP_POINT = stopPoint;
    }
    
    
    
    @Override
    protected double progress(double speed) {
        return 0; // In progress
    }

    @Override
    protected double progressSpeed() {
        if(this.DIST <= this.STOP_POINT - this.SERVICE_REF.getStoppingDistance()) {
            
        }
        return 0; // In progress
    }
    
    

}
