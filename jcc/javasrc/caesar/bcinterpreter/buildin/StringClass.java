package caesar.bcinterpreter.buildin;

import caesar.bcinterpreter.*;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 16:23
 */
public class StringClass extends CClass {

    public static final int code = 2;

    public StringClass(CaesarBCInterpreter cintr) {
        super(cintr);
        name = "String";
        mtab = new HashMap<Integer, CMethod>();
        mtab.put(CMethod.PRINT_METHOD_CODE, new CMethod(CMethod.PRINT_METHOD_CODE, "print") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject object = interpreter.getStack().popObject();
                String value = ByteConvertor.toString(object.getFieldsData());
                System.out.println(value);
            }
        });
        mtab.put(CMethod.INIT_METHOD_CODE, new CMethod(CMethod.INIT_METHOD_CODE, "init") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject object = interpreter.getStack().popObject();
            }
        });

        mtab.put(CMethod.ADD_METHOD_CODE, new CMethod(CMethod.ADD_METHOD_CODE, "add") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                CObject opB = interpreter.getStack().popObject();

                CObject result;


                String valueA = ByteConvertor.toString(opA.getFieldsData());

                // podle typu provedu soucet danym zpusobem
                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int ivalueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = new CObject(StringClass.code, ByteConvertor.toByta(valueA+ivalueB));
                        break;
                    case StringClass.code:
                        String svalueB = ByteConvertor.toString(opB.getFieldsData());
                        result = new CObject(StringClass.code, ByteConvertor.toByta(valueA+svalueB));
                        break;
                    default:
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA));
                }

                interpreter.getStack().pushObject(result);
            }
        });

    }

    @Override
    public int getObjectSize() {
        return -1;
    }

    @Override
    public int getCode() {
        return code;
    }
}
