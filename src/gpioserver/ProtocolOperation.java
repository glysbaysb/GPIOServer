package gpioserver;

/**
 *
 * @author 2016-12-27
 */
public class ProtocolOperation {
    private int op,
         dev,
         val;
    
    /**
     * The different operations of the protocol
     * todo: make this a class
     */
    public final byte LED_CHANGE = 0,
            SWITCH_CHANGE = 1;
   
    
    ProtocolOperation(byte[] buf) {
        op  = buf[0];
        dev = buf[1];
        val = buf[2];
    }
    
    public int getOperand() {
        return op;
    }
    
    public int getDevice() {
        return dev;
    }
    
    public int getVal() {
        return val;
    }
}
