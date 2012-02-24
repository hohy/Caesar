package caesar.bcinterpreter.buildin;

import caesar.bcinterpreter.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 23.2.12
 * Time: 18:36
 */
public class FileClass extends CClass {

    public static final int code = 7;
    
    public static final int READ_METHOD_CODE = 100;
    public static final int WRITE_METHOD_CODE = 100;

    public FileClass(CaesarBCInterpreter interpreter) {
        super(interpreter);
        name = "File";
        mtab = new HashMap<Integer, CMethod>();
        mtab.put(CMethod.INIT_METHOD_CODE, new CMethod(CMethod.INIT_METHOD_CODE, "init") {
            @Override
            public void execute(CaesarBCInterpreter interpreter) {

            }
        });

        List<String> readParams = new LinkedList<String>();
        readParams.add("file");
        mtab.put(READ_METHOD_CODE, new CMethod(READ_METHOD_CODE, "read") {
            @Override
            public CClass getReturnType() {
                return new ArrayClass(null);
            }

            @Override
            public void execute(CaesarBCInterpreter interpreter) {
                CObject param = interpreter.getStack().popObject();
                String fileName = ByteConvertor.toString(param.getFieldsData());
                CObject thisId = interpreter.getStack().popObject();
                List<Integer> numbers = new LinkedList<Integer>();
                try {
                    BufferedReader in = new BufferedReader(new FileReader(fileName));
                    String str;
                    while ((str = in.readLine()) != null) {
                        for(String s : str.trim().split(" ")) {
                            numbers.add(Integer.parseInt(s));
                        }
                    }
                    in.close();                    
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                int[] pointers = new int[numbers.size()];
                int index = 0;
                for(int number : numbers) {
                    CObject item = IntegerClass.createObject(number);
                    pointers[index++] = interpreter.getHeap().put(item);
                }
                
                CObject array = new CObject(ArrayClass.code, ByteConvertor.toByta(pointers));
                interpreter.getStack().pushObject(array);
                interpreter.setCurrentEnvironment(interpreter.getCurrentEnvironment().getSuperEnvironment());
            }
        });        
    }

    @Override
    public int getObjectSize() {
        return 0;  //To change body of implemented methods use FileClass | Settings | FileClass Templates.
    }

    @Override
    public int getCode() {
        return code;
    }
}
