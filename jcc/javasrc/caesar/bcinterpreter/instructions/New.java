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

        if(type==null) { // if type is unknown, set it to type of object on top of the stack.
            type = interpreter.getCClass(interpreter.getStack().peekObject().getTypeCode());
        }
        // create new empty object and push it to stack
        int objSize = type.getObjectSize() > 0 ? type.getObjectSize()-CClass.HEADER_SIZE : 1;
        CObject object = new CObject(typeCode, new byte[objSize]);
        int address = interpreter.getHeap().put(object);
        // add object info to environment
        interpreter.getCurrentEnvironment().add(id, address);

        interpreter.getStack().pushObject(object);
        // call init method on new object
        CMethod init = type.getMethod(CMethod.INIT_METHOD_CODE);
        if(init != null) init.execute(interpreter);
        else interpreter.getStack().popObject();

        // after object is initialize, pop it from stack and put it to the heap.
        object = interpreter.getStack().popObject();
        interpreter.getHeap().set(id, object);

    }    
}
