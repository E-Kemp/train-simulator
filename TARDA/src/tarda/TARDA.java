/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarda;

import java.util.Scanner;

/**
 * User IO class
 * @author mrp15ndu
 */
public class TARDA {
    public static final TrainMap TRAINS = new TrainMap();
    public static final STOMPListener STOMP = new STOMPListener();
    public static boolean stop = false;
    
    public static void main(String[] args) {
        System.out.printf("Train And Rail Data Accumulator v0.1 (Alpha)\n");
        
        STOMP.go();
        
//        Fix this later . . .
//        do {
//            switch(textMenu("What would you like to do?", "Run the listener, Display the trains, Clear the trains")) {
//                case 1:
//                    STOMP.go();
//                    break;
//                case 2:
//                    TRAINS.toString();
//                    break;
//                case 3:
//                    TRAINS.clear();
//                    System.out.println("Train list cleared");
//                    break;
//            }
//        } while(!stop);

    }
    
    /**
     * This method generates some of the menus used in the user IO
     * @param title
     * @param optionsInput
     * @return option that the user enters
     */
    private static int textMenu(String title, String optionsInput) {
        String[] options = optionsInput.split("\\,\\s+"); // Split the string into arrays at the points where there is a comma followed by a whitespace
        Scanner scan = new Scanner(System.in);
        StringBuilder output = new StringBuilder();
        int option;
        
        stop = false;
        
        output.append("--- ").append(title).append(" ---"); // Create the task question
        for(int optionNumber = 0; optionNumber < options.length; optionNumber++) // For loop to add the options and their numbers
            output.append("\n-- ").append(optionNumber+1).append(") ").append(options[optionNumber]);
        
        System.out.println(output.toString()); // Print the menu and options
        
        try {
            option = scan.nextInt();
            if(option <= options.length && option >= 1) // If the correct option is entered
                return option;
            else  if(option == 0) {
                stop = true;
                return 0;
            } else
                return 0;
        } catch(Exception e) {
            return 0;
        }
    }
    
}
