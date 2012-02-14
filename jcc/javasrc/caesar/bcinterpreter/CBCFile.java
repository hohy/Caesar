package caesar.bcinterpreter;

import caesar.bcinterpreter.CClass;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.2.12
 * Time: 11:48
 */
public class CBCFile implements Serializable {

    private List<CClass> classList;
    private byte[] bytecode;
    private byte[] constants;

    public CBCFile() {
    }

    public CBCFile(String fileName) {

    }
    
    public List<CClass> getClassList() {
        return classList;
    }

    public void setClassList(List<CClass> classList) {
        this.classList = classList;
    }

    public byte[] getBytecode() {
        return bytecode;
    }

    public void setBytecode(byte[] bytecode) {
        this.bytecode = bytecode;
    }

    public byte[] getConstants() {
        return constants;
    }

    public void setConstants(byte[] constants) {
        this.constants = constants;
    }
}


