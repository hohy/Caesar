package caesar.ast;

/**
 * Created by IntelliJ IDEA.
 * User: Jan Hýbl
 * Date: 13.12.11
 * Time: 17:16
 */
public class ReturnTree extends CommandTree {

    ExpressionTree expression;

    public ReturnTree(ExpressionTree expression) {
        this.expression = expression;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);
    }

    public ExpressionTree getExpression() {
        return expression;
    }
}
