/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpioserver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2016-12-27
 */
public class GPIOServer {
    static Process createProcess() throws IOException {
        ProcessBuilder pb =
            new ProcessBuilder("qemu-system-arm.exe", 
                    "-m", "128M", // RAM
                    "-machine", "raspi2", 
                    "-nographic", // console output, don't create a window
                    "-s", "-S"); // gdb server mode & suspend first
        //pb.directory(new File("C/Users/2016-12-27/AppData/Local/Temp/qemu/"));
        return pb.start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Process p;
        // First start the QEMU process because it needs a second or so
        try {
            p = createProcess();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.err.println("QEMU could not be started. Make sure all files "
                    + "are present");
            return;
        }
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Window w;
                try {
                    w = new Window(p);
                    w.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(GPIOServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
