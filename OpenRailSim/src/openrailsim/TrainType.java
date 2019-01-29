package openrailsim;

/**
 *
 * @author mrp15ndu
 */
public class TrainType {
    private final String TRN_CLASS;
    private final boolean MULTI_UNIT;
    private final int CARR_COUNT;
    private final double LENGTH; // in metres
    private final int POWER; // in horse power
    private final double MAX_TRAC_EFFORT; // in kN (kilo newtons)
    private final double CONT_TRAC_EFFORT; // in kN (kilo newtons)
    private final double MAX_SPEED; // in km/h
    private final double WEIGHT; // in tonnes (1000 KG)
    private enum tractionType {Electric, Diesel, Hybrid};
    
    /**
     * Main constructor, add more later
     * @param trnClass
     * @param multiUnit
     * @param carrCount
     * @param length
     * @param power
     * @param maxTrac
     * @param contTrac
     * @param maxSpeed
     * @param weight 
     */
    public TrainType(String trnClass, boolean multiUnit, int carrCount, double length, int power, double maxTrac, double contTrac, double maxSpeed, double weight) {
        this.TRN_CLASS = trnClass;
        this.MULTI_UNIT = multiUnit;
        this.CARR_COUNT = carrCount;
        this.LENGTH = length;
        this.POWER = power;
        this.MAX_TRAC_EFFORT = maxTrac;
        this.CONT_TRAC_EFFORT = contTrac;
        this.MAX_SPEED = maxSpeed;
        this.WEIGHT = weight;
    }
    
    public String getTrnClass() {
        return this.TRN_CLASS;
    }
    
    public double SimpleAcc() { // outputs in m/s^2
        return MAX_TRAC_EFFORT / WEIGHT;
    }
    
    
}
