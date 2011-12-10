package caesar.interpreter;

import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hohy
 */
public class InterpreterStack {
    
    private static Stack<Byte> stack;
    
    private InterpreterStack() {
        stack = new Stack<Byte>();
    }
    
    public static InterpreterStack getInstance() {
        return InterpreterStackHolder.INSTANCE;
    }

    public int popInteger() {
        byte[] data = new byte[4];
        for (int i = 0; i < data.length; i++) {
            data[i] = stack.pop();            
        }
        int result = ByteConvertor.toInt(data);
        logger.log(Level.FINEST, "Pop integer {0} from stack. Current stack size: {1}", new Object[] { result, stack.size() - 4});
        return result;
    }
    
    public double popDouble() {
        byte[] data = new byte[8];
        for (int i = 0; i < data.length; i++) {
            data[i] = stack.pop();            
        }
        double result = ByteConvertor.toDouble(data);
        logger.log(Level.FINEST, "Pop real {0} from stack. Current stack size: {1}", new Object[] {result, stack.size()});
        return result;
    }  
    
    public String popString() {
        int length = popInteger();
        byte[] data = new byte[length];
        for (int i = 0; i < data.length; i++) {
            data[i] = stack.pop();            
        }
        String result = ByteConvertor.toString(data);
        logger.log(Level.FINEST, "Pop string \"{0}\"from stack. Current stack size: {1}", new Object[] {result, stack.size()});
        return result;
    }
    
    public Boolean popBoolean() {
        byte[] data = new byte[1];
        for (int i = 0; i < data.length; i++) {
            data[i] = stack.pop();            
        }
        boolean result = ByteConvertor.toBoolean(data);
        logger.log(Level.FINEST, "Pop integer {0} from stack. Current stack size: {1}", new Object[] { result, stack.size() - 1});        
        return result;
    }

    public void pushInteger(int value) {
        logger.log(Level.FINEST, "Push to stack {0} integer.", value);
        byte[] data = ByteConvertor.toByta(value);
        this.push(data);
    }

    public void pushDouble(Double value) {
        logger.log(Level.FINEST, "Push to stack {0} double.", value);
        byte[] data = ByteConvertor.toByta(value);
        this.push(data);
    }

    public void pushString(String string) {
        logger.log(Level.FINEST, "Push to stack {0} string.", string);
        byte[] strdata = ByteConvertor.toByta(string);
        byte[] length = ByteConvertor.toByta(string.length());
        byte[] data = new byte[strdata.length + 4];
        System.arraycopy(length, 0, data, 0, 4);
        System.arraycopy(strdata, 0, data, 4, strdata.length);        
        this.push(data);
    }

    public void pushBoolean(boolean value) {
        logger.log(Level.FINEST, "Push to stack {0} double.", value);
        byte[] data = ByteConvertor.toByta(value);
        this.push(data);
    }
    
    private static class InterpreterStackHolder {

        private static final InterpreterStack INSTANCE = new InterpreterStack();
    }
    
    private static final Logger logger = Logger.getLogger(InterpreterStack.class.getName());

    public Object peek() {
        return stack.peek();
    }
    
    public byte[] pop(int objectSize) {
        logger.log(Level.FINEST, "Pop from stack {0} bytes. Current stack size: {1}", new Object[]{stack.peek(), stack.size() - objectSize});
        byte[] result = new byte[objectSize];
        for (int i = 0; i < result.length; i++) {
            result[i] = stack.pop();            
        }        
        
        return result;
    }

    public void push(byte[] array) {
        logger.log(Level.FINEST, "Push to stack {0} bytes. Current stack size: {1}", new Object[]{array.length , stack.size() + array.length});
        for (int i = 0; i < array.length; i++) {
            stack.push(array[array.length - i - 1]);            
        }
        
    }
    
}
