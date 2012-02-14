package caesar.ast;

import java.util.List;

/**
 * Represents code that access filed in class: myObject.myField
 * @author hohy
 */
public class ClassIdentifierTree extends IdentifierTree {
    private String fldName;
    private List<String> indetifiers;

    public ClassIdentifierTree(String name, String fldName) {
        super(name);
        this.fldName = fldName;
    }

    public ClassIdentifierTree(String name, List<String> identifiers) {
        super(name);
        this.indetifiers = identifiers;
    }    
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public String getFldName() {
        return fldName;
    }

    public List<String> getIndetifiers() {
        return indetifiers;
    }
}
