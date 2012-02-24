package caesar.bcinterpreter.buildin;

import caesar.ast.IdentifierTree;
import caesar.bcinterpreter.CClass;
import caesar.bcinterpreter.CField;
import caesar.bcinterpreter.CMethod;
import caesar.bcinterpreter.CaesarBCInterpreter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 19.2.12
 * Time: 11:02
 */
public class UserDefinedClass extends CClass implements Serializable {

    public int code;

    public UserDefinedClass(CaesarBCInterpreter interpreter) {
        super(interpreter);    //To change body of overridden methods use FileClass | Settings | FileClass Templates.
        fields = new LinkedList<CField>();
        mtab = new HashMap<Integer, CMethod>();
    }

    @Override
    public int getObjectSize() {
        return fields.size()*CaesarBCInterpreter.POINTER_SIZE + HEADER_SIZE;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void addMethod(int i, CMethod method) {
        this.mtab.put(i, method);
    }

    public void addField(String varName, CClass varType) {
        fields.add(new CField(varType,varName));
    }

    public List<CField> getFields() {
        return fields;
    }
}
