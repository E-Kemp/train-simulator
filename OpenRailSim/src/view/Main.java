package view;

import model.OpenRailSim;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;

/**
 * Main class containing all the use IO for the simulator
 * @author Elliot Jordan Kemp
 */
public class Main {
    
    OpenRailSim SIM;
    
    
    
    
    public static void main(String[] args) throws IOException {
        System.out.println("--- OpenRailSim ALPHA ---");
        System.out.println("Please enter the filename to use as a network");
        
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        
        try{
            List<String> lines = Files.readAllLines(Paths.get(input), StandardCharsets.UTF_8);

            JSONArray json = new JSONArray(lines);
        } catch(IOException e) {
            System.out.println("File isn't valid!");
        }
    }
}
