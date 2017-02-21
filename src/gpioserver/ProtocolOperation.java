package gpioserver;

/**
 *
 * @author 2016-12-27
 */
public class ProtocolOperation {
    private byte op_,
         dev_,
         val_;
    
    /**
     * The different operations of the protocol
     * todo: make this a class
     */
    public final static byte LED_CHANGE = 0,
            SWITCH_CHANGE = 1;
   
    /**
     * This constructor is used to "unmarshall" a packet.
     * Usually then getOperand() and such would be used to
     * read the actual values
     * @param buf content of that packet
     */
    ProtocolOperation(byte[] buf) {
        op_  = buf[0];
        dev_ = buf[1];
        val_ = buf[2];
    }
    
    /**
     * This constructor is used to "marshall" a packet.
     * Usually getBuffer() would be used to get a represenation that can be
     * sent over the network
     * @return 
     */
    ProtocolOperation(byte op, byte dev, byte val) {
        op_ = op;
        dev_ = dev;
        val_ = val;
    }
    
    public int getOperand() {
        return op_;
    }
    
    public int getDevice() {
        return dev_;
    }
    
    public int getVal() {
        return val_;
    }
    
    public byte[] getBuffer() {
        byte[] buf = new byte[3];
        buf[0] = op_;
        buf[1] = dev_;
        buf[2] = val_;
        
        return buf;
    }
}
