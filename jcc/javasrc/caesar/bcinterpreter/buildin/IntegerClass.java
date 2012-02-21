package caesar.bcinterpreter.buildin;

import caesar.bcinterpreter.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:57
 */
public class IntegerClass extends CClass implements Serializable {

    public static final int code = 1;
    
    public IntegerClass(CaesarBCInterpreter cintr) {
        super(cintr);
        name = "Integer";
        mtab = new HashMap<Integer, CMethod>();
        mtab.put(CMethod.PRINT_METHOD_CODE, new CMethod(CMethod.PRINT_METHOD_CODE) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject object = interpreter.getStack().popObject();
                int value = ByteConvertor.toInt(object.getFieldsData());
                System.out.println(value);
            }
        });

        mtab.put(CMethod.INIT_METHOD_CODE, new CMethod(CMethod.INIT_METHOD_CODE) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                // vyndam ze stacku prazdny int. Pod nim by se mel nachazet dalsi int, ktery bude ten novy inicializovat
                CObject object = interpreter.getStack().popObject();
            }
        });

        mtab.put(CMethod.ADD_METHOD_CODE, new CMethod(CMethod.ADD_METHOD_CODE) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                CObject result;

                CObject opB = interpreter.getStack().popObject();

                // podle typu provedu soucet danym zpusobem
                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opA.getFieldsData());
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA+valueB));
                        break;
                    default:
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA));
                }

                interpreter.getStack().pushObject(result);
            }
        });

        mtab.put(CMethod.EQ_METHOD_CODE, new CMethod(CMethod.EQ_METHOD_CODE) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                boolean result;

                CObject opB = interpreter.getStack().popObject();

                // podle typu provedu soucet danym zpusobem
                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = valueA == valueB;
                        break;
                    default:
                        result = false;
                }

                interpreter.getStack().pushBoolean(result);
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
