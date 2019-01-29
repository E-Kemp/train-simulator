package openrailsim;

import java.util.ArrayList;

/**
 * Class to represent a station with multiple platforms, extends ArrayList
 * @author mrp15ndu
 */
public class Station extends ArrayList<Platform> {

    private final String STN_CODE;
    private final String STN_TIPLOC;
    
    public Station(String code, String tiploc) {
        this.STN_CODE = code;
        this.STN_TIPLOC = tiploc;
    }
    
    public String getStnCode() {
        return this.STN_CODE;
    }
    
    public String getStnTIPLOC() {
        return this.STN_TIPLOC;
    }
}
