package model;

/**
 * A type of train that is used in service
 * @author Elliot Jordan Kemp
 */
public class TrainType {
    private enum TractionType {
        Electric, Diesel, Hybrid;        
    }
    
    private final String TRN_CLASS;
    private final boolean MULTI_UNIT;
    private final int CAR_COUNT;
    private final double CAR_LENGTH; // in metres
    private final double ACC; // in m/s/s
    private final double MAX_TRAC_EFFORT; // in kN (kilo newtons)
    private final double CONT_TRAC_EFFORT; // in kN (kilo newtons)
    private final double MAX_SPEED; // in km/h
    private final double WEIGHT; // in tonnes (1000 KG)
    private final TractionType TRAC_TYPE;
    
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Main constructor, requires all attributes
     * @param trnClass
     * @param multiUnit
     * @param carrCount
     * @param length
     * @param maxTrac
     * @param contTrac
     * @param maxSpeed
     * @param weight
     * @param type
     * @throws model.AttributeOutOfRangeException 
     */
    public TrainType(String trnClass, boolean multiUnit, int carrCount, double length, double maxTrac, double contTrac, double maxSpeed, double weight, int type) 
     throws AttributeOutOfRangeException {
        try {
            this.TRN_CLASS = trnClass;
            this.MULTI_UNIT = multiUnit;
            this.CAR_COUNT = carrCount;
            this.CAR_LENGTH = length;
            this.MAX_TRAC_EFFORT = maxTrac;
            this.CONT_TRAC_EFFORT = contTrac;
            this.MAX_SPEED = maxSpeed;
            this.WEIGHT = weight;
            this.TRAC_TYPE = TractionType.values()[type];
            
            this.ACC = 0;
        } catch(Exception e) {
            throw new AttributeOutOfRangeException("Train type invalid");
        }
    }
    
    public TrainType(String trnClass, boolean multiUnit, int carrCount, double length, double acc, double maxSpeed, double weight, int type) 
     throws AttributeOutOfRangeException {
        try {
            this.TRN_CLASS = trnClass;
            this.MULTI_UNIT = multiUnit;
            this.CAR_COUNT = carrCount;
            this.CAR_LENGTH = length;
            this.ACC = acc;
            this.MAX_SPEED = maxSpeed;
            this.WEIGHT = weight;
            this.TRAC_TYPE = TractionType.values()[type];
            
            this.MAX_TRAC_EFFORT = 0;
            this.CONT_TRAC_EFFORT = 0;
        } catch(Exception e) {
            throw new AttributeOutOfRangeException("Train type invalid");
        }
    }
    
    /**
     * Default constructor
     */
    public TrainType() {
        this.TRN_CLASS = "Default";
        this.MULTI_UNIT = false;
        this.CAR_COUNT = 0;
        this.CAR_LENGTH = 0;
        this.MAX_TRAC_EFFORT = 0;
        this.CONT_TRAC_EFFORT = 0;
        this.ACC = 0;
        this.MAX_SPEED = 0;
        this.WEIGHT = 0;
        this.TRAC_TYPE = TractionType.Electric;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    
    public String getTrnClass() { return this.TRN_CLASS; }
    
    public boolean isMU() { return this.MULTI_UNIT; }
    
    public int getCarCount() { return this.CAR_COUNT; }
    
    public double getLength() { return this.CAR_LENGTH; }
        
    public double getMaxTracEffort() { return this.MAX_TRAC_EFFORT; }
    
    public double getContTracEffort() { return this.CONT_TRAC_EFFORT; }
        
    public double getMaxSpeed() { return this.MAX_SPEED; }
    
    public double getWeight() { return this.WEIGHT; }
    
    public boolean isElectric() { return this.TRAC_TYPE.equals(TractionType.Electric); }
    public boolean isDiesel() { return this.TRAC_TYPE.equals(TractionType.Diesel); }
    public boolean isHybrid() { return this.TRAC_TYPE.equals(TractionType.Hybrid); }
    

    //Acceleration
    public double getAcc(double w) { //m/s^2
        return (this.MAX_TRAC_EFFORT*1000) / w;
    }
    public double getAcc() {
        if(this.ACC != 0)
            return this.ACC;
        else
            return getAcc(this.WEIGHT);
    }

    // </editor-fold>
    
    public static TrainType test() {
        try {
            return new TrainType("Class 90", false, 12, 18.75, 2, 177, 84.5, 1);
        } catch(AttributeOutOfRangeException e) {
            System.out.println("Something went wrong!");
            return null;
        }
    }
}
