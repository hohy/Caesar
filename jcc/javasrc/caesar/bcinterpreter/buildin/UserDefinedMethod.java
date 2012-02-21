package caesar.bcinterpreter.buildin;

import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CaesarBCInterpreter;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 19.2.12
 * Time: 16:16
 */
public class UserDefinedMethod extends CMethod {
    
    int bcPointer = 0;
    String name;

    public UserDefinedMethod(int bcPointer, String name, int code) {
        super(code);
        this.bcPointer = bcPointer;
        this.name = name;
    }

    @Override
    public void execute(CaesarBCInterpreter interpreter) {
        int retAddress = interpreter.getPc();
        interpreter.getStack().pushInt(retAddress);
        interpreter.setPc(bcPointer);
    }
}
