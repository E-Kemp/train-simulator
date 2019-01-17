package openrailsim;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;

public class OpenRailSim {

    public Graph<Point, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);
    
    //Default empty constructor
    public OpenRailSim(String filename) {
        
    }
    
    
    
}
