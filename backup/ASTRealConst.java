package caesar;
public class ASTRealConst extends SimpleNode {
  
  private double value;

  public ASTRealConst(int id) {
    super(id);
  }

  public ASTRealConst(Caesar p, int id) {
    super(p, id);
  }

  public void setValue(double d) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(CaesarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override
  public String toString()  {
    return "Real const: " + value;
  }

}
