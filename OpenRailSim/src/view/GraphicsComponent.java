package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OpenRailSim;
/**
 * Graphics component for the graphical representation of the sim
 * @author Elliot Jordan Kemp
 */
public class GraphicsComponent extends javax.swing.JPanel implements Runnable {

    @Override
    public void run() {
        do {
            changeTest();
            reDraw();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // very important
                break;
            }
        } while(!STOP);
    }
    
    private volatile boolean STOP = false;
    private final OpenRailSim SIM;
    private Graphics2D G;
    
    private final ArrayList<Shape> shapeList = new ArrayList<>();
    
    boolean test = false;    
    
    public GraphicsComponent(OpenRailSim sim) {
        super();
        this.SIM = sim;
        
        setBackground(Color.BLACK);
    }
    
    public GraphicsComponent() {
        super();
        this.SIM = new OpenRailSim();
        addTestShapes();
    }
    
    public void stopThread() {
        STOP = true;
    }
    
    public void reDraw() {
        paintComponent(this.getGraphics());
    }
    
    public void changeTest() {
        test = !test;
    }
    
    public void addTestShapes() {
        shapeList.add(new Rectangle(100, 100, 100, 100));
        shapeList.add(new Rectangle(300, 100, 100, 100));
    }
    
    private void drawComponents() {
        G.setColor(test ? Color.WHITE : Color.YELLOW);
        shapeList.forEach((s) -> {
            G.fill(s);
        });
    }
    
    private void drawTest() {
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
