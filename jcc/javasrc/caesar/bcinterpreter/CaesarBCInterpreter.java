package caesar.bcinterpreter;


import caesar.bcinterpreter.buildin.*;
import caesar.bcinterpreter.instructions.*;

import javax.swing.*;
import java.io.*;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:20
 */
public class CaesarBCInterpreter {

    private static final Logger logger = Logger.getLogger(CaesarBCInterpreter.class.getName());

    public static final int POINTER_SIZE = 4;
    public static final int CLASS_ID_SIZE = 4;
    public static final int SIZE_INFO_SIZE = 4;

    private Heap heap;
    private Stack stack;
    private Constants constants;
    private Environment currentEnvironment;
    
    private byte[] bytecode;
    private Map<Integer, CClass> classMap;
    
    private int pc;

    public CaesarBCInterpreter() {
        logger.info("Caesar interpreter!!!");
        logger.finer("Logging test...");
    }

    public void init() {
        logger.finer("Initializing interpreter...");
        heap = new Heap(1000000, this);
        stack = new Stack(1000000);
        currentEnvironment = new Environment(null);

        classMap = new HashMap<Integer, CClass>();
        // add build in classes
        loadClass(new IntegerClass(this));
        loadClass(new StringClass(this));
        loadClass(new DemoClass(this));
        loadClass(new ArrayClass(this));
        loadClass(new MethodsClass(this));

    }

    public void loadClass(CClass cls) {
        classMap.put(cls.getCode(), cls);
    }

    public void loadFile(String path) {
        logger.finer("Loading program file: " + path);
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
        constants.setInterpreter(this);
    }
    
    public void run() {
        logger.finer("Running program...");
        while (pc < bytecode.length) {
            byte code = bytecode[pc++];
            switch (code) {
                case Print.code:
                    Print.execute(this);
                    break;
                case PushConstant.code:
                    PushConstant.execute(this);
                    break;
                case New.code:
                    New.execute(this);
                    break;
                case Load.code:
                    Load.execute(this);
                    break;
                case LoadField.code:
                    LoadField.execute(this);
                    break;
                case Set.code:
                    Set.execute(this);
                    break;
                case SetField.code:
                    SetField.execute(this);
                    break;
                case Point.code:
                    Point.execute(this);
                    break;
                case PointField.code:
                    PointField.execute(this);
                    break;
                case Jump.code:
                    Jump.execute(this);
                    break;
                case Call.code:
                    Call.execute(this);
                    break;
                case Return.code:
                    Return.execute(this);
                    break;
                case ReturnTop.code:
                    ReturnTop.execute(this);
                    break;
                case NewEnv.code:
                    NewEnv.execute(this);
                    break;
                case Add.code:
                    Add.execute(this);
                    break;
                case Sub.code:
                    Sub.execute(this);
                    break;
                case Mul.code:
                    Mul.execute(this);
                    break;
                case Div.code:
                    Div.execute(this);
                    break;
                case Equal.code:
                    Equal.execute(this);
                    break;
                case NotEqual.code:
                    NotEqual.execute(this);
                    break;
                case Greater.code:
                    Greater.execute(this);
                    break;
                case Lower.code:
                    Lower.execute(this);
                    break;
                case GreaterEqual.code:
                    GreaterEqual.execute(this);
                    break;
                case LowerEqual.code:
                    LowerEqual.execute(this);
                    break;
                case JumpIfFls.code:
                    JumpIfFls.execute(this);
                    break;
                case JumpIfTrue.code:
                    JumpIfTrue.execute(this);
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

    public Environment getCurrentEnvironment() {
        return currentEnvironment;
    }

    public void setCurrentEnvironment(Environment currentEnvironment) {
        this.currentEnvironment = currentEnvironment;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }
}
