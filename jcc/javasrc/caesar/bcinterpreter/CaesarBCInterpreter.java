package caesar.bcinterpreter;


import caesar.bcinterpreter.buildin.IntegerClass;
import caesar.bcinterpreter.instructions.Print;
import caesar.bcinterpreter.instructions.PushConstant;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:20
 */
public class CaesarBCInterpreter {

    public static final int POINTER_SIZE = 4;
    public static final int CLASS_ID_SIZE = 4;
    public static final int SIZE_INFO_SIZE = 4;

    private Heap heap;
    private Stack stack;
    private Constants constants;
    
    private byte[] bytecode;
    private Map<Integer, CClass> classMap;
    
    private int pc;
    private int sp;

    public void init() {

        heap = new Heap(1000000);
        stack = new Stack(1000000);

        classMap = new HashMap<Integer, CClass>();
        // add build in classes
        CClass cls = new IntegerClass(this);
        classMap.put(cls.getCode(), cls);

    }

    public void loadFile(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream io = new ObjectInputStream(fis);
            CBCFile cbcFile = (CBCFile) io.readObject();
            io.close();

            loadFile(cbcFile);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void loadFile(CBCFile cbcFile) {
        for(CClass cls : cbcFile.getClassList()) {
            classMap.put(cls.getCode(), cls);
        }

        bytecode = cbcFile.getBytecode();
        constants = new Constants(cbcFile.getConstants());
    }
    
    public void run() {

        while (pc < bytecode.length) {
            byte code = bytecode[pc++];
            switch (code) {
                case Print.code:
                    Print.execute(this);
                    break;
                case PushConstant.code:
                    PushConstant.execute(this);
                    break;
            }
        }

    }

    public CClass getCClass(int typeCode) {
        return classMap.get(typeCode);
    }

    public int readIntInstructionParam() {
        int result = readIntFromBytearray(pc, bytecode);
        pc += 4;
        return result;
    }
    
    public static int readIntFromBytearray(int address, byte[] array) {
        byte[] bytes = new byte[4];
        System.arraycopy(array, address, bytes, 0, bytes.length);
        return ByteConvertor.toInt(bytes);
    }

    // Getters
    
    public Heap getHeap() {
        return heap;
    }

    public Stack getStack() {
        return stack;
    }

    public Constants getConstants() {
        return constants;
    }

    public byte[] getBytecode() {
        return bytecode;
    }

    public int getPc() {
        return pc;
    }

    public int getSp() {
        return sp;
    }

}
