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
        mtab.put(CMethod.PRINT_METHOD_CODE, new CMethod(CMethod.PRINT_METHOD_CODE, "print") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject object = interpreter.getStack().popObject();
                int value = ByteConvertor.toInt(object.getFieldsData());
                System.out.println(value);
            }
        });

        mtab.put(CMethod.INIT_METHOD_CODE, new CMethod(CMethod.INIT_METHOD_CODE, "init") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                // vyndam ze stacku prazdny int. Pod nim by se mel nachazet dalsi int, ktery bude ten novy inicializovat
                CObject object = interpreter.getStack().popObject();
            }
        });

        mtab.put(CMethod.ADD_METHOD_CODE, new CMethod(CMethod.ADD_METHOD_CODE, "add") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                CObject result;

                CObject opB = interpreter.getStack().popObject();

                // podle typu provedu soucet danym zpusobem
                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA+valueB));
                        break;
                    default:
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA));
                }

                interpreter.getStack().pushObject(result);
            }
        });

        mtab.put(CMethod.SUB_METHOD_CODE, new CMethod(CMethod.SUB_METHOD_CODE, "sub") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                CObject result;

                CObject opB = interpreter.getStack().popObject();

                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA-valueB));
                        break;
                    default:
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA));
                }

                interpreter.getStack().pushObject(result);
            }
        });

        mtab.put(CMethod.MUL_METHOD_CODE, new CMethod(CMethod.MUL_METHOD_CODE, "mul") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                CObject result;

                CObject opB = interpreter.getStack().popObject();

                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA*valueB));
                        break;
                    default:
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA));
                }

                interpreter.getStack().pushObject(result);
            }
        });

        mtab.put(CMethod.DIV_METHOD_CODE, new CMethod(CMethod.DIV_METHOD_CODE, "div") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                CObject result;

                CObject opB = interpreter.getStack().popObject();

                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        int r = valueA/valueB;
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(r));
                        break;
                    default:
                        result = new CObject(IntegerClass.code, ByteConvertor.toByta(valueA));
                }

                interpreter.getStack().pushObject(result);
            }
        });

        mtab.put(CMethod.EQ_METHOD_CODE, new CMethod(CMethod.EQ_METHOD_CODE, "equals") {
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

        mtab.put(CMethod.NE_METHOD_CODE, new CMethod(CMethod.NE_METHOD_CODE, "notEquals") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                boolean result;

                CObject opB = interpreter.getStack().popObject();

                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = valueA != valueB;
                        break;
                    default:
                        result = false;
                }

                interpreter.getStack().pushBoolean(result);
            }
        });

        mtab.put(CMethod.GT_METHOD_CODE, new CMethod(CMethod.GT_METHOD_CODE, "greater") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                boolean result;

                CObject opB = interpreter.getStack().popObject();

                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = valueA > valueB;
                        break;
                    default:
                        result = false;
                }

                interpreter.getStack().pushBoolean(result);
            }
        });

        mtab.put(CMethod.LT_METHOD_CODE, new CMethod(CMethod.LT_METHOD_CODE, "lower") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                boolean result;

                CObject opB = interpreter.getStack().popObject();

                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = valueA < valueB;
                        break;
                    default:
                        result = false;
                }

                interpreter.getStack().pushBoolean(result);
            }
        });

        mtab.put(CMethod.GE_METHOD_CODE, new CMethod(CMethod.GE_METHOD_CODE, "greaterEquals") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                boolean result;

                CObject opB = interpreter.getStack().popObject();

                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = valueA >= valueB;
                        break;
                    default:
                        result = false;
                }

                interpreter.getStack().pushBoolean(result);
            }
        });

        mtab.put(CMethod.LE_METHOD_CODE, new CMethod(CMethod.LE_METHOD_CODE, "lowerEquals") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject opA = interpreter.getStack().popObject();
                int valueA = ByteConvertor.toInt(opA.getFieldsData());

                boolean result;

                CObject opB = interpreter.getStack().popObject();

                switch (opB.getTypeCode()) {
                    case IntegerClass.code:
                        int valueB = ByteConvertor.toInt(opB.getFieldsData());
                        result = valueA <= valueB;
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
