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
    
    public GraphicsComponent() {
        super();
        this.SIM = new OpenRailSim();
    }
    
    private void drawComponents() {
    }
    
    private void drawTest() {
        G.setColor(Color.WHITE);
        G.fillRect(100, 100, 100, 100);
        G.setColor(Color.YELLOW);
        G.fillRect(300, 100, 100, 100);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.G = (Graphics2D) g;
        setBackground(Color.BLACK);
        
        drawTest();
        
        drawComponents();
    }
    
}
