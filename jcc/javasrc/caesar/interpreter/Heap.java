package caesar.interpreter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hohy
 */
public class Heap {
    
    private byte[] data;
    private static final int HEAP_SIZE = 1000000;
    private int pointer = 0;
    
    private static final Logger logger = Logger.getLogger(Heap.class.getName());
    
    private Heap() {
        data = new byte[HEAP_SIZE];
    }
    
    public static Heap getInstance() {
        return HeapHolder.INSTANCE;
    }

    int store(byte[] data) {
        int result = pointer;
        System.arraycopy(data, 0, this.data, pointer, data.length);
        pointer += data.length;
        return result;
    }

    byte[] get(int pointer, int objectSize) {
        logger.log(Level.FINEST, "Reading object of size {0} from pointer {1}.", new Object[]{objectSize, pointer}); 
        byte[] result = new byte[objectSize];
        System.arraycopy(data, pointer, result, 0, objectSize);
        return result;
    }

    // Store data to heap at pointer location    
    void store(byte[] data, int pointer) {
        System.arraycopy(data, 0, this.data, pointer, data.length);
    }
    
    private static class HeapHolder {

        private static final Heap INSTANCE = new Heap();
    }
}
