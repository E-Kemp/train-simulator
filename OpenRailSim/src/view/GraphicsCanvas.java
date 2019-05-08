package view;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.event.MouseInputListener;
import model.OpenRailSim;
import model.Service;
/**
 * Graphics component for the graphical representation of the simulator
 * @author Elliot Jordan Kemp
 */
public final class GraphicsCanvas extends Canvas implements Runnable, MouseMotionListener, MouseInputListener {
    
    /**
     *
     */
    @Override
    public void run() {
        do {
            //changeTest();
            reDraw();
            try {
                Thread.sleep(1000/60);
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
    private final LinkedHashMap<String, Service> SERVICES;
    
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
    public GraphicsCanvas(OpenRailSim sim) {
        super();
        this.SIM = sim;
        this.MAP_LINES = sim.getMap();
        this.SERVICES = sim.cacheServices();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }
    
    /**
     * Default constructor
     */
    public GraphicsCanvas() {
        super();
        this.SIM = null;
        this.MAP_LINES = null;
        this.SERVICES = null;
    }
    
    /**
     * Redraw components
     */
    public void reDraw() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics2D g = (Graphics2D) bf.getDrawGraphics();
        
        setBackground(Color.WHITE);
        
        this.G = (Graphics2D) g;
        G.clearRect(0, 0, getWidth(), getHeight());
        
        drawComponents();
        
        G.dispose();
        bf.show();
    }
    
    
    private void drawComponents() {
        AffineTransform start = G.getTransform();
        
        
        O();
        G.setStroke(new BasicStroke(2));
        
        
        drawMap();
        drawServices();
        
        G.setTransform(start);
        drawText();
        
        //G.drawString("KEK", 100, 100);
    }
    
    /**
     * Translate to the origin
     */
    private void O() {
        G.translate(this.getWidth()/2, this.getHeight()/2);
        G.scale(1, -1);
        G.translate(X, Y); // Can be moved if mouse is dragged
    }
    
    private void drawMap() {
        G.setColor(Color.BLACK);
        this.MAP_LINES.forEach(l -> {
            //System.out.printf("%d, %d, %d, %d\n", (int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
            G.drawLine((int)l.getX1(), (int)l.getY1(), (int)l.getX2(), (int)l.getY2());
        });
    }
    
    private void drawServices() {
        G.setColor(Color.RED);
        this.SERVICES.forEach((str, s) -> {
            //System.out.printf("%s: %f\n", "Angle: ", SIM.getAngle(s.getCurrentIndex()));
            AffineTransform af = G.getTransform();
                G.translate(s.getCurrentVert().x, s.getCurrentVert().y);
                G.rotate(s.getAngle());
                G.fillRect(-4, (int)serviceProg(s)-10, 8, 20);
                //G.drawString("Test", 0, 0);
                //G.drawString("Fuck", 0.0f, serviceProg(s));
            G.setTransform(af);
        });
    }
    
    private void drawText() {
        int textOffset = 10;
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(2);
        G.setFont(new Font("Serif", Font.PLAIN, 12));
        G.setColor(Color.BLACK);
        for(Service s : this.SERVICES.values()) {
            G.drawString(String.format("%10s: %5s", "Service", s.getHeadcode()), textOffset, 20);
            G.drawString(String.format("%10s: %5d", "Point", s.getCurrentMapIndex()), textOffset, 40);
            G.drawString(String.format("%10s: %5s km", "Dist", formatter.format(s.getDistance())), textOffset, 60);
            G.drawString(String.format("%10s: %5s kmh", "Speed", formatter.format(s.getSpeed())), textOffset, 80);
            G.drawString(String.format("%10s: %s", "Points", s.getCurrentEdge().getPointsString()), textOffset, 100);
            textOffset += 150;
        }
    }
    
    private float serviceProg(Service s) {
        //System.out.println(s.getCurrentEdge().getLength());
        //System.out.println(s.getCurrentEdge().getEdgeLength());
        if(s.isRunning())
        return (((float)s.getCurrentEdge().getEdgeLength()/(float)s.getCurrentEdge().getLength()) 
                * (float)s.getDistance());
        else return 0;
//        System.out.println(s.getCurrentEdge().getEdgeLength());
//        System.out.println(prog);

        
    }

    
    // <editor-fold desc=" Mouse events ">

    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();
        this.X = this.X_ORG - (this.X_CUR - point.x);
        this.Y = this.Y_ORG + (this.Y_CUR - point.y); // Flipped because java coordinates
 
//        if (this.DRAGGING) {
//            repaint();
//        }
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
