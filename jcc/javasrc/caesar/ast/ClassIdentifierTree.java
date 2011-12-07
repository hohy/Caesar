package caesar.ast;

/**
 * Represents code that access filed in class: myObject.myField
 * @author hohy
 */
public class ClassIdentifierTree extends IdentifierTree {
    private String fldName;

    public ClassIdentifierTree(String name, String fldName) {
        super(name);
        this.fldName = fldName;
    }
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public String getFldName() {
        return fldName;
    }

    
    
}
