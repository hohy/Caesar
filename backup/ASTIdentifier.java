package caesar;

public class ASTIdentifier extends SimpleNode {
  private String name;

  public ASTIdentifier(int id) {
    super(id);
  }

  public ASTIdentifier(Caesar p, int id) {
    super(p, id);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(CaesarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override
  public String toString() {
    return "Identifier: " + name;
  }
}