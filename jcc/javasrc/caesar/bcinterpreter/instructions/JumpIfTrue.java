package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 22.2.12
 * Time: 22:47
 */
public class JumpIfTrue {
    public static final byte code = 0x51;

    public static void execute(CaesarBCInterpreter interpreter) {

        boolean conditionResult = interpreter.getStack().popBoolean();
        int jmpAddress = interpreter.readIntInstructionParam();

        if(conditionResult) interpreter.setPc(jmpAddress);
    }
}
