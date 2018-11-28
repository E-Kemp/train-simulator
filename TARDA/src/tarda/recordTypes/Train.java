/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarda.recordTypes;

/**
 *
 * @author mrp15ndu
 */
public class Train {
    public final String rID;
    public final String trainID;
    public final String uID;
    
    public Train(String rID, String trainID, String uID) {
        this.rID = rID;
        this.trainID = trainID;
        this.uID = uID;
    }
    
    public boolean isTrain(String uID) {
        return uID.equals(this.uID);
    }
    
    @Override
    public String toString() {
        return String.format("%10s %s\n%10s %s\n%10s %s\n",
                "Ride ID:", this.rID, 
                "Headcode:", this.trainID,
                "Unique ID:", this.uID);
    }
}
