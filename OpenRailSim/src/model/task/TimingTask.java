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
    
    /**
     * Default constructor (don't use this!)
     */
    public TimingTask() {
        super(null, null, null);
        this.TASK_TYPE = null;
        this.START_TIME = 0;
    }

    /**
     * Constructor with full parameters
     * @param taskType
     * @param index
     * @param time
     */
    public TimingTask(TaskType taskType, int index, int time) {
        super(null, null, null); // placeholder, feature creep laterrrr
        this.TASK_TYPE = taskType;
        this.START_TIME = this.CUR_TIME = time*10;
    }
    
    /**
     * @return start time of the task
     */
    public int getStartTime() {
        return this.START_TIME;
    }
    
    /**
     * @return current time of the task
     */
    public int getCurTime() {
        return this.CUR_TIME;
    }
    
    /**
     * Count down method to 0
     * @return
     */
    public int countDown() {
        if(!DONE) {
            this.CUR_TIME--;
            if(this.CUR_TIME <= 0)
                finish();
            return this.CUR_TIME;
        }
        return 0;
    }
    
    /**
     * @return type of task
     */
    public TaskType getTask() {
        return this.TASK_TYPE;
    }
    
    /**
     * Finish the task
     * @return true
     */
    public boolean finish() {
        return DONE = true;
    }
    
        
    @Override
    protected double progress(double speed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected double progressSpeed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
