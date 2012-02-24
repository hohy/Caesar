package caesar.bcinterpreter.buildin;

import caesar.bcinterpreter.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 22.2.12
 * Time: 9:40
 */
public class ArrayClass extends CClass {
    
    public static final int code = 6;
    
    private static final int SET_SIZE_CODE = 10;
    private static final int GET_SIZE_CODE = 13;
    private static final int GET_CODE = 11;
    private static final int SET_CODE = 12;
    
    public ArrayClass(CaesarBCInterpreter interpreter) {
        super(interpreter);
        name = "Array";
        mtab = new HashMap<Integer, CMethod>();
        mtab.put(CMethod.INIT_METHOD_CODE, new CMethod(CMethod.INIT_METHOD_CODE, "init") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject o = interpreter.getStack().popObject();
                if(interpreter.getStack().isEmpty() || interpreter.getStack().peekObject().getTypeCode() != ArrayClass.code) {
                    interpreter.getStack().pushObject(o);
                }
            }
        });

        List<String> setSizeParams = new LinkedList<String>();
        setSizeParams.add("size");
        mtab.put(SET_SIZE_CODE, new CMethod(SET_SIZE_CODE, "setSize", setSizeParams) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject param = interpreter.getStack().popObject();
                int size = ByteConvertor.toInt(param.getFieldsData());
                CObject thisId = interpreter.getStack().popObject();
                int id = ByteConvertor.toInt(thisId.getFieldsData());
                System.out.println("Create array with size " + size);
                int[] pointers = new int[size];
                for(int i = 0; i < size; i++) {
                    CObject item = IntegerClass.createObject(0);
                    pointers[i] = interpreter.getHeap().put(item);
                }

                CObject array = new CObject(code, ByteConvertor.toByta(pointers));
                int newAddress = interpreter.getHeap().put(array);
                interpreter.getCurrentEnvironment().set(id, newAddress);
                interpreter.setCurrentEnvironment(interpreter.getCurrentEnvironment().getSuperEnvironment());
            }
        });

        List<String> getSizeParams = new LinkedList<String>();
        mtab.put(GET_SIZE_CODE, new CMethod(GET_SIZE_CODE, "getSize", getSizeParams) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject thisId = interpreter.getStack().popObject();
                int id = ByteConvertor.toInt(thisId.getFieldsData());
                CObject thisObj = interpreter.getHeap().get(id);
                int size = thisObj.getFieldsData().length/CaesarBCInterpreter.POINTER_SIZE;
                CObject sizeObj = IntegerClass.createObject(size);
                interpreter.getStack().pushObject(sizeObj);
                interpreter.setCurrentEnvironment(interpreter.getCurrentEnvironment().getSuperEnvironment());
            }

            @Override
            public CClass getReturnType() {
                return new IntegerClass(null);
            }
        });

        List<String> getParams = new LinkedList<String>();
        getParams.add("index");
        mtab.put(GET_CODE, new CMethod(GET_CODE, "get", getParams) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject param = interpreter.getStack().popObject();
                int index = ByteConvertor.toInt(param.getFieldsData());
                CObject thisId = interpreter.getStack().popObject();
                int id = ByteConvertor.toInt(thisId.getFieldsData());
                
                CObject array = interpreter.getHeap().get(id);
                
                int itemAddressPointer = interpreter.getCurrentEnvironment().get(id) + array.getFieldAddress(index);
                int itemPointer = interpreter.getHeap().getInt(itemAddressPointer);

                CObject item = interpreter.getHeap().getFromAddress(itemPointer);

                interpreter.getStack().pushObject(item);
                interpreter.setCurrentEnvironment(interpreter.getCurrentEnvironment().getSuperEnvironment());
            }
        });

        List<String> setParams = new LinkedList<String>();
        getParams.add("index");
        getParams.add("value");
        mtab.put(SET_CODE, new CMethod(SET_CODE, "set", setParams) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject value = interpreter.getStack().popObject();

                CObject param = interpreter.getStack().popObject();
                int index = ByteConvertor.toInt(param.getFieldsData());

                CObject thisId = interpreter.getStack().popObject();
                int id = ByteConvertor.toInt(thisId.getFieldsData());

                CObject array = interpreter.getHeap().get(id);
                int itemAddressPointer = interpreter.getCurrentEnvironment().get(id) + array.getFieldAddress(index);
                int itemPointer = interpreter.getHeap().put(value);

                interpreter.getHeap().set(itemAddressPointer, itemPointer);
                interpreter.setCurrentEnvironment(interpreter.getCurrentEnvironment().getSuperEnvironment());
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
