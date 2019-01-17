package openrailsim;

import java.util.Scanner;

/**
 * Main class containing all the use IO for the simulator
 * @author mrp15ndu
 */
public class Main {
    
    OpenRailSim sim;
    
    public static void main(String[] args) {
        System.out.println("--- OpenRailSim ALPHA ---");
        System.out.println("Please enter the filename to use as a network");
        
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        
        System.out.println(input);
    }
}
