/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import model.OpenRailSim.PointList;

/**
 * Large thanks to GeeksForGeeks for help with the algorithm
 * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
 * @author mrp15ndu
 */
public class DijkstraPath {
    
    class Node {
        protected final Node PARENT;
        protected final TrackPoint POINT;
        protected final double DISTANCE;
        protected final ArrayList<Node> CHILDREN;
        

        public Node(Node PARENT, TrackPoint POINT, double DISTANCE) {
            this.PARENT = PARENT;
            this.POINT = POINT;
            this.DISTANCE = DISTANCE;
            this.CHILDREN = new ArrayList<>();
        }
    }
    
    private final OpenRailSim SIM;
    private final int V;
    
    public DijkstraPath(OpenRailSim sim) {
        this.SIM = sim;
        this.V = sim.vertexSet().size();
        
        
    }
    
    public int getIndex(PointList vertSet, TrackPoint point) {
        return vertSet.indexOf(point.getCode());
    }
    
    /**
     * 
     * @param src
     * @param dest
     * @return 
     */
    public String[] path(String src, String dest) {
        double[] dist = new double[V];
        int min_index = 0;
        double min_current = 0;
        
        LinkedList<String> tmpPath = new LinkedList<>();
        LinkedList<String> path = new LinkedList<>();
        
        // Set all values except root to max
        for (int i = 1; i < V; i++) {
            dist[i] = Double.MAX_VALUE;
        }
        dist[0] = 0;

        // Re-orders the list to set the selected code as the new root
        // Casting here may be problematic
        PointList vertSet = (PointList) this.SIM.vertexSet().subList(this.SIM.getVertIndex(src), V);
        PointList vertexEnd = (PointList) this.SIM.vertexSet().subList(0, this.SIM.getVertIndex(src));
        Collections.reverse(vertexEnd);
        vertSet.addAll(vertexEnd);
        
        // Make sure each vertex is unmarked
        vertSet.forEach(vertex -> vertex.unmark()); 
        
        // Mark the root as that's the start point
        vertSet.get(0).mark();

        
        for(int i = 0; !TrackPoint.allMarked(vertSet); i++) {
            vertSet.get(i).mark(); // Mark the vertex as visited
            TrackPoint p = vertSet.get(i);
            for(TrackSegment n : p.getEdges()) {
                double tmp = dist[i] + n.getLength();
                if(tmp < dist[getIndex(vertSet, n.getTarget(p))]) {
                    dist[getIndex(vertSet, n.getTarget(p))] = tmp;
                    //Logic for best path here . . .
                }
            }
        }
        
        System.out.println(Arrays.toString(dist));
        
        //Node tree = this.minSpanningTree(new Node(null, vertSet.get(0), 0));
        
        
        return null; // Placeholder
    }
    
}
