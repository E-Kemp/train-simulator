/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.task;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author elliot
 */
public class TaskQueue extends LinkedList<AbstractTask> {

    public TaskQueue() {
        super();
    }
    
    public TaskQueue(List<AbstractTask> l) {
        super(l);
    }
}
