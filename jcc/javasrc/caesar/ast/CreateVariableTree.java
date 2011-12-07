package caesar.ast;

/**
 * 
 * @author hohy
 */
public class CreateVariableTree extends CommandTree {

    IdentifierTree identifier;
    ExpressionTree exp;

    public CreateVariableTree(IdentifierTree identifier, ExpressionTree exp) {
        this.identifier = identifier;
        this.exp = exp;
    }
    
    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public ExpressionTree getExp() {
        return exp;
    }

    public IdentifierTree getIdentifier() {
        return identifier;
    }
    
}
