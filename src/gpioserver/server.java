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
                
                System.out.print("packet recvd\n");
            }
            catch(Exception e) {
                System.err.print(e);
            }
        }
    }
}
