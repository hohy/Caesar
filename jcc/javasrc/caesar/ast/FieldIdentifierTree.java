package caesar.ast;

/**
 *
 * @author hohy
 */
public class FieldIdentifierTree extends IdentifierTree {
    ExpressionTree exp;
    

    public FieldIdentifierTree(String name) {
        super(name);
    }

    public FieldIdentifierTree(String name, ExpressionTree exp) {
        super(name);
        this.exp = exp;
    }    

    public ExpressionTree getExp() {
        return exp;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }
}
