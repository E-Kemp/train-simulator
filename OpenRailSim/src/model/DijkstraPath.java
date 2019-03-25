/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mrp15ndu
 */
public class DijkstraPath {
    
    public class Node<T> {
        private T data;
        private int dist;
        private Node<T> parent;
        private List<Node<T>> children;
    }
    
    private final OpenRailSim SIM;
    
    public DijkstraPath(OpenRailSim sim) {
        this.SIM = sim;
    }

    public DijkstraPath() {
        this.SIM = null;
    }
    
    public String[] getPath(String[] codes) {
        boolean finished;
        for (int i = 0; i < codes.length-1; i++) {
            Node<TrackPoint> t = new Node<>();
        }
        
        return null;
    }
    
    
    
}
