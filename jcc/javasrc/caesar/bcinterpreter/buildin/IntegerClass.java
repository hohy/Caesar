package caesar.bcinterpreter.buildin;

import caesar.bcinterpreter.*;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:57
 */
public class IntegerClass extends CClass {

    public IntegerClass(CaesarBCInterpreter cintr) {
        super(cintr);
        name = "Integer";
        mtab = new HashMap<Integer, CMethod>();
        mtab.put(CMethod.PRINT_METHOD_CODE, new CMethod() {
            @Override
            public void execute() {
                CObject object = interpreter.getStack().popObject();
                int value = ByteConvertor.toInt(object.getFieldsData());
                System.out.println(value);
            }
        });
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
