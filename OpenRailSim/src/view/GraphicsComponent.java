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
import java.util.HashMap;
import java.util.List;
import javax.swing.event.MouseInputListener;
import model.OpenRailSim;
import model.Service;
/**
 * Graphics component for the graphical representation of the simulator
 * @author Elliot Jordan Kemp
 */
public final class GraphicsComponent extends javax.swing.JPanel implements Runnable, MouseMotionListener, MouseInputListener {
    
    
    @Override
    public void run() {
        this.SERVICES.forEach((str, s) -> s.start());
        do {
            //changeTest();
            reDraw();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // very important
                break;
            }
        } while(!STOP);
    }

    
    private volatile boolean STOP = false;
    private final OpenRailSim SIM;
    private Graphics2D G;
    private final ArrayList<Shape> SHAPE_LIST = new ArrayList<>();
    private final List<Line2D> MAP_LINES;
    
    //Cached for ease of use
    private final HashMap<String, Service> SERVICES;
    
    boolean DRAGGING;
    private int X = 0;
    private int Y = 0;
    private int X_ORG = 0;
    private int Y_ORG = 0;
    private int X_CUR = 0;
    private int Y_CUR = 0;
    

    
    boolean test = false;    
    
    /**
     * Constructor from the Sim
     * @param sim 
     */
    public GraphicsComponent(OpenRailSim sim) {
        super();
        this.SIM = sim;
        this.MAP_LINES = sim.getMap();
        this.SERVICES = sim.cacheServices();
        addTestShapes();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }
    
    public GraphicsComponent() {
        super();
        this.SIM = null;
        this.MAP_LINES = null;
        this.SERVICES = null;
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
        G.translate(X, Y); // Can be moved if mouse is dragged
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
        G.setColor(/*test ? Color.BLACK : */Color.RED);
        //G.translate(this.getWidth()/2, this.getHe ight()/2);
        
        O();
        G.setStroke(new BasicStroke(2));
        
        
        drawMap();
        
        G.drawString("KEK", 100, 100);
        
        drawServices();
        
    }
    
    private void drawMap() {
        this.MAP_LINES.forEach((l) -> {
            //System.out.printf("%d, %d, %d, %d\n", (int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
            G.drawLine((int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
        });
    }
    
    private void drawServices() {
        this.SERVICES.forEach((str, s) -> {
            if(s.isRunning()) {
                //G.translate(s.getCurrentVert().x, s.getCurrentVert().y);
                //G.rotate(this.SIM.getAngle(s.getCurrentIndex()));
                //G.translate(0, s.progress(RATE));
                double progress = s.progress(this.SIM.RATE);
                System.out.println(progress);
//                G.create();
//                G.translate(
//                        s.getCurrentVert().x + (int) (progress*Math.sin(SIM.getAngle(s.getCurrentIndex()))),
//                        s.getCurrentVert().y + (int) (progress*Math.cos(SIM.getAngle(s.getCurrentIndex()))));
//                G.drawString("Fuck", 0, -10);
                G.drawString("FUCK", 
                    s.getCurrentVert().x + (int) (progress*Math.sin(SIM.getAngle(s.getCurrentIndex()))), 
                    s.getCurrentVert().y + (int) (progress*Math.cos(SIM.getAngle(s.getCurrentIndex()))));
            }
//            
        });
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.G = (Graphics2D) g;
        setBackground(Color.WHITE);
        
        drawComponents();
    }

    
    // <editor-fold desc=" Mouse events ">
    
    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();
        this.X = this.X_ORG - (this.X_CUR - point.x);
        this.Y = this.Y_ORG + (this.Y_CUR - point.y); // Flipped because java coordinates
 
        if (this.DRAGGING) {
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        System.out.println("mousePressed at " + point);
        this.X_CUR = point.x;
        this.Y_CUR = point.y;
        this.DRAGGING = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point point = e.getPoint();
        this.X_ORG = this.X;
        this.Y_ORG = this.Y;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    // </editor-fold>
    
    
    
    
}
