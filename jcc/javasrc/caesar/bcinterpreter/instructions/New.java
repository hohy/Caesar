package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.2.12
 * Time: 10:12
 */
public class New {
    public static final byte code = 0x03;

    public static void execute(CaesarBCInterpreter interpreter) {
        // read type of new object
        int typeCode = interpreter.readIntInstructionParam();
        int id = interpreter.readIntInstructionParam();
        CClass type = interpreter.getCClass(typeCode);

        // create new empty object and push it to stack
        CObject object = new CObject(typeCode, new byte[type.getObjectSize()]);
        interpreter.getStack().pushObject(object);

        // call init method on new object
        CMethod init = type.getMethod(CMethod.INIT_METHOD_CODE);
        init.execute();

        // after object is initialize, pop it from stack and put it to the heap.
        object = interpreter.getStack().popObject();
        int address = interpreter.getHeap().put(object);

        // add object info to environment
        interpreter.getCurrentEnvironment().add(id, address);
    }    
}
