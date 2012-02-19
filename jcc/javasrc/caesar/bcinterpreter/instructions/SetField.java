package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 18.2.12
 * Time: 18:24
 */
public class SetField {
    public static final byte code = 0x07;

    public static void execute(CaesarBCInterpreter interpreter) {
        int id = interpreter.readIntInstructionParam();
        int address = interpreter.getStack().popInt() + CClass.HEADER_SIZE + id*CaesarBCInterpreter.POINTER_SIZE;
        CObject obj = interpreter.getStack().popObject();
        int pointer = interpreter.getHeap().put(obj);
        
        interpreter.getHeap().set(address,pointer);
    }
}
