package caesartests.bcinterpreter;

import caesar.bcinterpreter.CBCFile;
import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CaesarBCInterpreter;
import caesar.bcinterpreter.buildin.IntegerClass;
import caesar.bcinterpreter.instructions.Print;
import caesar.bcinterpreter.instructions.PushConstant;
import caesar.interpreter.ByteConvertor;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 12:11
 */
public class HelloWorldTest {

    @Test
    public void hello() {

        CBCFile file = new CBCFile();
        byte[] testbc = {PushConstant.code, 0x00, 0x00, 0x00, 0x00, Print.code};
        byte[] consts = new byte[1000];
        int[] obj = {1, 12, 12345};
        byte[] intconst = ByteConvertor.toByta(obj);
        System.arraycopy(intconst,0,consts,0,intconst.length);
        file.setClassList(new LinkedList<CClass>());
        file.setBytecode(testbc);
        file.setConstants(consts);

        CaesarBCInterpreter interpreter = new CaesarBCInterpreter();

        interpreter.init();
        interpreter.loadFile(file);

        interpreter.run();

    }
}
