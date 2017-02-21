/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpioserver;
import java.io.IOException;
import java.net.*;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author 2016-12-27
 */
public class Server extends SwingWorker<Void, ProtocolOperation> {
    DatagramSocket socket;
    Window w;
    
    public Server(Window w_) throws IOException {
        this(w_, 30000);
    }

    public Server(Window w_, int port) throws IOException {
        socket = new DatagramSocket(port);
        w = w_;
    } 
    
    @Override
    protected Void doInBackground() {
        while(!isCancelled()) {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                
                publish(new ProtocolOperation(buf));
            }
            catch(Exception e) {
                System.err.print(e);
            }
        }
        
        return null;
    }
    
    @Override
    protected void process(List<ProtocolOperation> ops) {
        ops.forEach((_item) -> {
            if(_item.getOperand() == _item.LED_CHANGE) {
                w.ledStatusChange(_item.getDevice(), _item.getVal() > 0);
            }
            });
    }
}
