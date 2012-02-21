package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CObject;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 21.2.12
 * Time: 13:10
 */
public class ReturnTop {

    public static final byte code = 0x0D;

    public static void execute(CaesarBCInterpreter interpreter) {
        CObject result = interpreter.getStack().popObject();
        int address = interpreter.getStack().popInt();
        interpreter.getStack().pushObject(result);
        interpreter.setPc(address);
    }

}
