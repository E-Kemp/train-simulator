package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import model.OpenRailSim;
import model.TrackPoint;
import model.TrackSegment;
import org.jgrapht.traverse.BreadthFirstIterator;
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
    //Affine transform applies a direct transormation matrix
    
    
    private final ArrayList<Shape> shapeList = new ArrayList<>();
    
    boolean test = false;    
    
    public GraphicsComponent(OpenRailSim sim) {
        super();
        this.SIM = sim;
        addTestShapes();
    }
    
    public GraphicsComponent() {
        super();
        SIM = null;
        addTestShapes();
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
    }
    
    public void changeTest() {
        test = !test;
    }
    
    public void addTestShapes() {
        shapeList.add(new Rectangle(0, 0, 100,   100));
        shapeList.add(new Rectangle(50, 50, 100, 100));
        shapeList.add(new Ellipse2D.Float(-50, 50, 50, 50));
    }
    
    private void drawComponents() {
        G.setColor(test ? Color.WHITE : Color.YELLOW);
        //G.translate(this.getWidth()/2, this.getHe ight()/2);
        O();
        
        shapeList.forEach((s) -> {
            G.fill(s);
            addMap();
        });

        
        G.dispose();
        
        
    }
    
    private void addMap() {        
        TrackPoint t = this.SIM.vertexSet().iterator().next(); //First TrackPoint
        BreadthFirstIterator<TrackPoint, TrackSegment> it = new BreadthFirstIterator<>(this.SIM);
        ArrayList<int[]> i = new ArrayList<>();
        it.forEachRemaining(p -> {
            
        });
        
//        for (int i = 0; i < coords.length; i++) {
//            for (int j = 0; j < 2; j++) {
//                coords[i][j] *= 100;
//            }
//        }
        
//        for (int i = 0; i < coords.length-1; i++) {
//            G.drawLine(coords[i][0], coords[i][1], coords[i+1][0], coords[i+1][1]);
//        }
//        G.drawLine(coords[coords.length-1][0], coords[coords.length-1][1], coords[0][0], coords[0][1]);
    }
    
    //weird errors fix this
    private void drawCoords(TrackPoint t) {
        if(this.SIM.outgoingEdgesOf(t).size() > 0) {
            Iterator<TrackSegment> it = this.SIM.outgoingEdgesOf(t).iterator();
            it.forEachRemaining(s -> {
                TrackPoint r = this.SIM.getEdgeTarget(s);
                G.drawLine(t.x, t.y, r.x, r.y);
                //drawCoords(r);
            });
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.G = (Graphics2D) g;
        setBackground(Color.BLACK);
        
        drawComponents();
    }

    
    
}
