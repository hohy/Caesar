package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 21.2.12
 * Time: 15:58
 */
public class JumpIfFls {
    public static final byte code = 0x50;

    public static void execute(CaesarBCInterpreter interpreter) {
        
        boolean conditionResult = interpreter.getStack().popBoolean();
        int jmpAddress = interpreter.readIntInstructionParam();
        
        if(conditionResult) interpreter.setPc(jmpAddress);
    }    
}
