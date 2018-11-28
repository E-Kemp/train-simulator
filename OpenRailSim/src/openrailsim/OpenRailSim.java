package openrailsim;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;

public class OpenRailSim {

    public Graph<TIPLOC, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);
    
    //Default empty constructor
    public OpenRailSim() {
        
    }
    
    
    public static void main(String[] args) {
    }
    
}
