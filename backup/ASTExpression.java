package caesar;

public class ASTExpression extends SimpleNode {
  private Operator operator;

 public ASTExpression(int id) {
    super(id);
  }

  public ASTExpression(Caesar p, int id) {
    super(p, id);
  }

  public void setOperator(Operator op) {
    this.operator = op;
  }

  public Operator getOperator() {
    return operator;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(CaesarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override
  public String toString() {
    return "Expression: " + operator;
  }
}
