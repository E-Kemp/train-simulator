package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import model.OpenRailSim;
/**
 * Graphics component for the graphical representation of the sim
 * @author Elliot Jordan Kemp
 */
public class GraphicsComponent extends javax.swing.JPanel {
    
    private final OpenRailSim SIM;
    private Graphics2D G;
    
    public GraphicsComponent(OpenRailSim sim) {
        super();
        this.SIM = sim;
        
        setBackground(Color.BLACK);
    }
    
    private void drawComponents() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.G = (Graphics2D) g;
        
        drawComponents();
    }
    
}
