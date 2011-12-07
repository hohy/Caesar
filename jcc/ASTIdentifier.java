
public class ASTIdentifier extends SimpleNode {
  private String name;

  public void setName(String name) {
    this.name = name;
  }

  public String getName(String name) {
    return name;
  }

  @Override
  public String toString() {
    return "Identifier: " + name;
  }
}