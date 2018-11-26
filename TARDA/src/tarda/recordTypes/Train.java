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
    public final String trainID;
    public final String uID;
    
    public Train(String uID, String trainID) {
        this.uID = uID;
        this.trainID = trainID;
    }
}
