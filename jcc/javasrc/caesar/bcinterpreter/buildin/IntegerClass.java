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

    public static final int code = 1;
    
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

        mtab.put(CMethod.INIT_METHOD_CODE, new CMethod() {
            @Override
            public void execute() {
                // vyndam ze stacku prazdny int. Pod nim by se mel nachazet dalsi int, ktery bude ten novy inicializovat
                CObject object = interpreter.getStack().popObject();
            }
        });
    }

    @Override
    public int getObjectSize() {
        return HEADER_SIZE + 4;
    }

    @Override
    public int getCode() {
        return code;
    }

    public static CObject createObject(int i) {
        byte[] data = ByteConvertor.toByta(i);
        return new CObject(code, data);
    }
}
