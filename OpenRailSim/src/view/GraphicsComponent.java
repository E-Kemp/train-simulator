package view;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputListener;
import model.OpenRailSim;
import model.TrackPoint;
import model.TrackSegment;
/**
 * Graphics component for the graphical representation of the sim
 * @author Elliot Jordan Kemp
 */
public class GraphicsComponent extends javax.swing.JPanel implements Runnable, MouseMotionListener, MouseInputListener {
    
    
    @Override
    public void run() {
        T.start();
    }
    
    Thread T = new Thread(new Runnable() {
        @Override
        public void run() {
            do {
                //changeTest();
                reDraw();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); // very important
                    break;
                }
            } while(!STOP);
        }
    });
    
    private volatile boolean STOP = false;
    private final OpenRailSim SIM;
    private Graphics2D G;
    
    private int X = 0;
    private int Y = 0;
    
    private int X_ORG = 0;
    private int Y_ORG = 0;
    
    private final ArrayList<Shape> SHAPE_LIST = new ArrayList<>();
    private final List<Line2D> MAP_LINES;
    
    boolean test = false;    
    
    public GraphicsComponent(OpenRailSim sim) {
        super();
        this.SIM = sim;
        this.MAP_LINES = sim.getMap();
        addTestShapes();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }
    
    public GraphicsComponent() {
        super();
        this.SIM = null;
        this.MAP_LINES = new ArrayList<>();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }
    
    public void stopThread() {
        STOP = true;
    }
    
    public void reDraw() {
        paintComponent(this.getGraphics());
    }
    
    /**
     * Translate to the origin
     */
    private void O() {
        G.translate(this.getWidth()/2, this.getHeight()/2);
        G.scale(1, -1);
        G.translate(X, Y);
    }
    
    public void changeTest() {
        test = !test;
    }
    
    public void addTestShapes() {
        SHAPE_LIST.add(new Rectangle(0, 0, 100,   100));
        SHAPE_LIST.add(new Rectangle(50, 50, 100, 100));
        SHAPE_LIST.add(new Ellipse2D.Float(-50, 50, 50, 50));
    }
    
    private void drawComponents() {
        G.setColor(test ? Color.BLACK : Color.RED);
        //G.translate(this.getWidth()/2, this.getHe ight()/2);
        O();
        
        //SHAPE_LIST.forEach((s) -> G.fill(s));
        
        G.setStroke(new BasicStroke(2));
        drawMap();
        
        G.dispose();
        
        
    }
    
    private void drawMap() {
        this.MAP_LINES.forEach((l) -> {
            System.out.printf("%d, %d, %d, %d\n", (int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
            G.drawLine((int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
        });
    }
    
    MouseEvent pressed;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.G = (Graphics2D) g;
        setBackground(Color.WHITE);
        
        drawComponents();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point coords = new Point(e.getX(), e.getY());
        this.X = this.X - (pressed.getX() + e.getX());
        this.Y = this.Y + (pressed.getY() + e.getY());
        this.reDraw();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        pressed = e;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
