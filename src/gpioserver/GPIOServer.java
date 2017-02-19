/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpioserver;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2016-12-27
 */
public class GPIOServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Window w = new Window();
                w.setVisible(true);
                try {
                    (new Thread(new server(w))).start();
                } catch (IOException ex) {
                    System.err.print(ex);
                    return;
                }
            }
        });
    }
    
}
