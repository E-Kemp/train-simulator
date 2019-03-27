/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Large thanks to GeeksForGeeks for help with the algorithm
 * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
 * @author mrp15ndu
 */
public class DijkstraPath {
    
    private final OpenRailSim SIM;
    private final int V;
    
    public DijkstraPath(OpenRailSim sim) {
        this.SIM = sim;
        this.V = sim.vertexSet().size();
    }
    
    /**
     * Method taken from GeeksForGeeks
     * @param dist
     * @param mark
     * @return minimum distance from the vertex to the source(?)
     */
    private int minDistance(int dist[], boolean mark[]) { 
        return 0;
    } 
    
    public String[] path(String src, String dest) {
        int[] dist = new int[V];
        boolean[] mark = new boolean[V];
        
        for (int i = 0; i < V; i++) { 
            dist[i] = Integer.MAX_VALUE; 
            mark[i] = false; 
        } 
        
        // Re-orders the list to set the selected code as the new root
        List<TrackPoint> vertexSet = this.SIM.vertexSet().subList(this.SIM.getVertIndex(src), V);
        List<TrackPoint> vertexEnd = this.SIM.vertexSet().subList(0, this.SIM.getVertIndex(src));
        Collections.reverse(vertexEnd);
        vertexSet.addAll(vertexEnd);
        
        // Set the root distance to 0
        dist[this.SIM.getVertIndex(src)] = 0;
        
        for (int i = 0; i < V - 1; i++) {
            // Get the closest vertex
            int u = minDistance(dist, mark);
            
            // Mark the vertex  
            mark[u] = true;
            
        }
        
        return null; // Placeholder
    }
    
    
    
}
