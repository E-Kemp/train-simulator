/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarda;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;

/**
 *
 * @author mrp15ndu
 */
public class newTARDA {
   
    public static void main(String[] args) throws Exception {
        StompConnection connection = new StompConnection();
        connection.open("datafeeds.networkrail.co.uk", 61618);
        connection.connect("e.kemp@uea.ac.uk", "11644f37-78d4-475b-9aa6-ee01aee1b31d");
        StompFrame connect = connection.receive();
        if (!connect.getAction().equals(Stomp.Responses.CONNECTED)) {
            throw new Exception ("Not connected");
        }
        connection.begin("tx1");
        connection.send("/topic/test", "message1", "tx1", null);
        connection.send("/topic/test", "message2", "tx1", null);
        connection.commit("tx1");
        connection.subscribe("/topic/test", Subscribe.AckModeValues.CLIENT);
        connection.begin("tx2");
        
        StompFrame message = connection.receive();
        System.out.println(message.getBody());
        connection.ack(message, "tx2");
        
        message = connection.receive();
        System.out.println(message.getBody());
        connection.ack(message, "tx2");
        
        connection.commit("tx2");
        connection.disconnect();
    }
}
