/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpioserver;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author 2016-12-27
 */
public class server implements Runnable {
    /**
     * The different operations of the protocol
     */
    final byte LED_CHANGE = 0,
            SWITCH_CHANGE = 1;
    
    DatagramSocket socket;
    Window w;
    
    public server(Window w_) throws IOException {
        this("server", w_);
    }

    public server(String name, Window w_) throws IOException {
        socket = new DatagramSocket(30000);
        w = w_;
    } 
    
    @Override
    public void run(){
        while(true) {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                
                byte op  = buf[0];
                byte dev = buf[1];
                byte val = buf[2];
                if(op == LED_CHANGE)
                    w.ledStatusChange(dev, val > 0);
            }
            catch(Exception e) {
                System.err.print(e);
            }
        }
    }
}
