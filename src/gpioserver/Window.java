/*
 * todo: cancel thread when the window closes
 */
package gpioserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Main class of the GPIO Server. Does three things:
 * * handles all GUI operations
 * * checks whether QEMU is still alive
 * * handles all network thingies
 * @author 2016-12-27
 */
public class Window extends javax.swing.JFrame {
    private Server server;
    private Process qemu;
    private Timer qemuTimer;
    
    public void ledStatusChange(int which, boolean turnedOn) {
        Map<Integer, JLabel> mapping = new HashMap<>();
        mapping.put(5, LED1);
        mapping.put(6, LED2);
        mapping.put(7, LED3);
        mapping.put(8, LED4);
        mapping.put(9, LED5);
        mapping.put(10, LED6);
        mapping.put(11, LED7);
        mapping.put(12, LED8);
        
        JLabel a = mapping.get(which);
        try {           
            BufferedImage imgOn = ImageIO.read(Window.class.getResource("/resources/licht-an.png"));
            ImageIcon iconOn = new ImageIcon(imgOn);

            BufferedImage imgOff = ImageIO.read(Window.class.getResource("/resources/licht-aus.png"));
            ImageIcon iconOff = new ImageIcon(imgOff);

            a.setIcon(turnedOn ? iconOn : iconOff);
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Create & send a network packet describing the status change
     * 
     * Called whenever a switch / toggle switch changes its value.
     * @param which which switch / toggle switch (numbered 1 - 8)
     * @param newValue pressed = 1, unpressed = 0
     */
    private void sendSwitchChange(byte which, byte newValue) {
        try {
            ProtocolOperation p = new ProtocolOperation(
                    ProtocolOperation.SWITCH_CHANGE,
                    which,
                    newValue);
            
            byte[] data = p.getBuffer();
            InetAddress address = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(data, data.length,
                    address, 30001);
            
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
        } catch (SocketException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates new form Window
     * @param p the qemu process
     * @throws java.io.IOException
     */
    public Window(Process p) throws IOException {
        initComponents();
        
        (server = new Server(this)).execute();
        
        /* create a timer that checks whether QEMU is still alive and
           prints all output */
        qemu = p;
        ActionListener taskPerformer = (ActionEvent evt) -> {
            if(!qemu.isAlive()) {
                this.dispose();
                this.formWindowClosing(new WindowEvent(this, 0));
                return;
            }
            
            try {
                if(qemu.getInputStream().available() > 0) 
                {
                    byte[] b = new byte[80];
                    qemu.getInputStream().read(b);
                    System.out.print(">" + new String(b));
                }
                
                if(qemu.getErrorStream().available() > 0) 
                {
                    byte[] b = new byte[80];
                    qemu.getErrorStream().read(b);
                    System.err.print("!" + new String(b));
                }
                
                qemuTimer.restart();
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }         
        };
        qemuTimer = new Timer(500, taskPerformer);
        qemuTimer.start();
        
        
        JLabel[] arr = {LED1, LED2, LED3, LED4, LED5, LED6, LED7, LED8};
        for(int i = 0; i < arr.length; i++) {
            BufferedImage imgOff = ImageIO.read(Window.class.getResource("/resources/licht-aus.png"));
            ImageIcon iconOff = new ImageIcon(imgOff);
            
            arr[i].setIcon(iconOff);
            arr[i].setText("");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        switches = new javax.swing.JPanel();
        switch4 = new javax.swing.JButton();
        switch1 = new javax.swing.JButton();
        switch2 = new javax.swing.JButton();
        switch3 = new javax.swing.JButton();
        schalter1 = new javax.swing.JToggleButton();
        schalter2 = new javax.swing.JToggleButton();
        schalter3 = new javax.swing.JToggleButton();
        schalter4 = new javax.swing.JToggleButton();
        leds = new javax.swing.JPanel();
        LED3 = new javax.swing.JLabel();
        LED1 = new javax.swing.JLabel();
        LED4 = new javax.swing.JLabel();
        LED5 = new javax.swing.JLabel();
        LED2 = new javax.swing.JLabel();
        LED6 = new javax.swing.JLabel();
        LED8 = new javax.swing.JLabel();
        LED7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GPIO Server");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        switches.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        switch4.setText("Taster 4");
        switch4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch4ActionPerformed(evt);
            }
        });

        switch1.setText("Taster 1");
        switch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch1ActionPerformed(evt);
            }
        });

        switch2.setText("Taster 2");
        switch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch2ActionPerformed(evt);
            }
        });

        switch3.setText("Taster 3");
        switch3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch3ActionPerformed(evt);
            }
        });

        schalter1.setText("Schalter 1");
        schalter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schalter1ActionPerformed(evt);
            }
        });

        schalter2.setText("Schalter 2");
        schalter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schalter2ActionPerformed(evt);
            }
        });

        schalter3.setText("Schalter 3");
        schalter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schalter3ActionPerformed(evt);
            }
        });

        schalter4.setText("Schalter 4");
        schalter4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schalter4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout switchesLayout = new javax.swing.GroupLayout(switches);
        switches.setLayout(switchesLayout);
        switchesLayout.setHorizontalGroup(
            switchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(switchesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(switch1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(switch2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(switch3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(switch4)
                .addGap(18, 18, 18)
                .addComponent(schalter1, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(schalter2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(schalter3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(schalter4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        switchesLayout.setVerticalGroup(
            switchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, switchesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(switchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(schalter1, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, switchesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(switch4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(switch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(switch2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(switch3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(schalter4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(schalter3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(schalter2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        leds.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        LED3.setText("AUS");
        LED3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LED1.setText("AUS");
        LED1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LED4.setText("AUS");
        LED4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LED5.setText("AUS");
        LED5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LED2.setText("AUS");
        LED2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LED6.setText("AUS");
        LED6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LED8.setText("AUS");
        LED8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LED7.setText("AUS");
        LED7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout ledsLayout = new javax.swing.GroupLayout(leds);
        leds.setLayout(ledsLayout);
        ledsLayout.setHorizontalGroup(
            ledsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ledsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LED1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LED2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LED3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LED4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addComponent(LED5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LED6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LED7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LED8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        ledsLayout.setVerticalGroup(
            ledsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ledsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ledsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LED3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LED1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LED4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LED5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LED2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LED6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LED8, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LED7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(switches, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(leds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(switches, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void releaseSwitch(byte which) {
        ActionListener taskPerformer = (ActionEvent evt) -> {
            sendSwitchChange(which, (byte)0);
        };
        Timer timer = new Timer(100, taskPerformer);
        timer.setRepeats(false);
        timer.start();
    }
    
    private void switch4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch4ActionPerformed
        sendSwitchChange((byte)17, (byte)1);
        releaseSwitch((byte)17);
    }//GEN-LAST:event_switch4ActionPerformed

    private void switch3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch3ActionPerformed
        sendSwitchChange((byte)16, (byte)1);
        releaseSwitch((byte)16);
    }//GEN-LAST:event_switch3ActionPerformed

    private void switch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch2ActionPerformed
        sendSwitchChange((byte)3, (byte)1);
        releaseSwitch((byte)3);
    }//GEN-LAST:event_switch2ActionPerformed

    private void switch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch1ActionPerformed
        sendSwitchChange((byte)2, (byte)1);
        releaseSwitch((byte)2);
    }//GEN-LAST:event_switch1ActionPerformed

    private void schalter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schalter1ActionPerformed
        sendSwitchChange((byte)18, (byte)(schalter1.isSelected() ? 1 : 0));
    }//GEN-LAST:event_schalter1ActionPerformed

    private void schalter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schalter2ActionPerformed
        sendSwitchChange((byte)19, (byte)(schalter1.isSelected() ? 1 : 0));
    }//GEN-LAST:event_schalter2ActionPerformed

    private void schalter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schalter3ActionPerformed
        sendSwitchChange((byte)20, (byte)(schalter1.isSelected() ? 1 : 0));
    }//GEN-LAST:event_schalter3ActionPerformed

    private void schalter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schalter4ActionPerformed
        sendSwitchChange((byte)21, (byte)(schalter1.isSelected() ? 1 : 0));
    }//GEN-LAST:event_schalter4ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        qemu.destroyForcibly();
        
        try {
            Stream<Path> files = Files.list(Paths.get(""));
            files.forEach((_item) -> {
                
                try {
                    if(_item.toString().startsWith("trace-")) {
                        System.out.println(_item.toString());
                        Files.delete(_item);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.exit(-1);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LED1;
    private javax.swing.JLabel LED2;
    private javax.swing.JLabel LED3;
    private javax.swing.JLabel LED4;
    private javax.swing.JLabel LED5;
    private javax.swing.JLabel LED6;
    private javax.swing.JLabel LED7;
    private javax.swing.JLabel LED8;
    private javax.swing.JPanel leds;
    private javax.swing.JToggleButton schalter1;
    private javax.swing.JToggleButton schalter2;
    private javax.swing.JToggleButton schalter3;
    private javax.swing.JToggleButton schalter4;
    private javax.swing.JButton switch1;
    private javax.swing.JButton switch2;
    private javax.swing.JButton switch3;
    private javax.swing.JButton switch4;
    private javax.swing.JPanel switches;
    // End of variables declaration//GEN-END:variables
}
