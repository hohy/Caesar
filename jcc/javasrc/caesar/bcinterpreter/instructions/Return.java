package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 19.2.12
 * Time: 14:22
 */
public class Return {
    public static final byte code = 0x0B;

    public static void execute(CaesarBCInterpreter interpreter) {
        int address = interpreter.getStack().popInt();
        interpreter.setPc(address);
    }
}
