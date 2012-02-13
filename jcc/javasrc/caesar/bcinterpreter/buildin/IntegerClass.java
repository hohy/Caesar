package caesar.bcinterpreter.buildin;

import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CField;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:57
 */
public class IntegerClass extends CClass {

    public IntegerClass() {
        name = "Integer";
    }

    @Override
    public int getObjectSize() {
        return HEADER_SIZE + 4;
    }

    @Override
    public int getCode() {
        return 1;
    }
}
