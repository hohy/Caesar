package caesar.bcinterpreter.buildin;

import caesar.bccompiler.CaesarBCCompiler;
import caesar.bcinterpreter.*;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 14.2.12
 * Time: 15:13
 */
public class DemoClass extends CClass {
    
    public static final int code = 3;

    public DemoClass(final CaesarBCInterpreter cintr) {
        super(cintr);
        name = "Demo";
        fields = new LinkedList<CField>();
        fields.add(new CField(this, "next"));
        fields.add(new CField(new IntegerClass(interpreter), "value"));

        mtab = new HashMap<Integer, CMethod>();

        // init methoda vytvori novou instanci teto tridy. Ta bude obsahovat 3 next urovně. Kazda uroven je oznacena cislem ve value.
        mtab.put(CMethod.INIT_METHOD_CODE, new CMethod(CMethod.INIT_METHOD_CODE) {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                interpreter.getStack().popObject();
                byte[] objData = new byte[fields.size()*CaesarBCInterpreter.POINTER_SIZE];
                int nextPoiner = -1;
                byte[] bytes = ByteConvertor.toByta(nextPoiner);
                System.arraycopy(bytes, 0, objData, 0, CaesarBCInterpreter.POINTER_SIZE);
                int level_addr = interpreter.getHeap().put(IntegerClass.createObject(3));
                bytes = ByteConvertor.toByta(level_addr);
                System.arraycopy(bytes, 0, objData, CaesarBCInterpreter.POINTER_SIZE, CaesarBCInterpreter.POINTER_SIZE);
                CObject l3 = new CObject(code,objData);
                nextPoiner = interpreter.getHeap().put(l3);
                bytes = ByteConvertor.toByta(nextPoiner);
                System.arraycopy(bytes, 0, objData, 0, CaesarBCInterpreter.POINTER_SIZE);
                level_addr = interpreter.getHeap().put(IntegerClass.createObject(2));
                bytes = ByteConvertor.toByta(level_addr);
                System.arraycopy(bytes, 0, objData, CaesarBCInterpreter.POINTER_SIZE, CaesarBCInterpreter.POINTER_SIZE);
                l3 = new CObject(code,objData);
                nextPoiner = interpreter.getHeap().put(l3);
                bytes = ByteConvertor.toByta(nextPoiner);
                System.arraycopy(bytes, 0, objData, 0, CaesarBCInterpreter.POINTER_SIZE);
                level_addr = interpreter.getHeap().put(IntegerClass.createObject(1));
                bytes = ByteConvertor.toByta(level_addr);
                System.arraycopy(bytes, 0, objData, CaesarBCInterpreter.POINTER_SIZE, CaesarBCInterpreter.POINTER_SIZE);
                l3 = new CObject(code,objData);
                interpreter.getStack().pushObject(l3);
            }
        });
    }

    @Override
    public int getObjectSize() {
        return CClass.HEADER_SIZE + fields.size()*CaesarBCInterpreter.POINTER_SIZE;
    }

    @Override
    public int getCode() {
        return code;
    }
}
