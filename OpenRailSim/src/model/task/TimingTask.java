/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.task;

/**
 * THIS IS TOTALLY BROKEN FIX ME LATER
 * @author 100128483
 */
public class TimingTask extends AbstractTask {


    
    public static enum TaskType {WAIT, STATION}
    
    private final TaskType TASK_TYPE;
    private final int START_TIME;
    private int CUR_TIME;
    private boolean DONE = false;
    
    
    public TimingTask() {
        super(null, null, null);
        this.TASK_TYPE = null;
        this.START_TIME = 0;
    }

    public TimingTask(TaskType taskType, int index, int time) {
        super(null, null, null); // placeholder, feature creep laterrrr
        this.TASK_TYPE = taskType;
        this.START_TIME = this.CUR_TIME = time*10;
    }
    
    public int getStartTime() {
        return this.START_TIME;
    }
    
    public int getCurTime() {
        return this.CUR_TIME;
    }
    
    public int countDown() {
        if(!DONE) {
            this.CUR_TIME--;
            if(this.CUR_TIME <= 0)
                finish();
            return this.CUR_TIME;
        }
        return 0;
    }
    
    public TaskType getTask() {
        return this.TASK_TYPE;
    }
    
    public boolean finish() {
        return DONE = true;
    }
    
    
    // Abstract implementation
    
    @Override
    protected double progress(double speed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected double progressSpeed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
