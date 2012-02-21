package caesar.bcinterpreter.instructions;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 19.2.12
 * Time: 14:29
 */
public class Call {
    public static final byte code = 0x0C;

    public static void execute(CaesarBCInterpreter interpreter) {
        int clsId = interpreter.readIntInstructionParam();
        int mthdId = interpreter.readIntInstructionParam();
        CClass cls = interpreter.getCClass(clsId);
        CMethod mth = cls.getMethod(mthdId);
        mth.execute(interpreter);
    }
}
