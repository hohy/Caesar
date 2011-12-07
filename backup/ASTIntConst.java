package caesar;

public class ASTIntConst extends SimpleNode {

  private int value;

  public ASTIntConst(int id) {
    super(id);
  }

  public ASTIntConst(Caesar p, int id) {
    super(p, id);
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(CaesarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override
  public String toString() {
    return "Integer const: " + value;
  }
}
