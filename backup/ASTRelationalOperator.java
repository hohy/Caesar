public class ASTRelationalOperator extends SimpleNode {
  
    private Operator operator;
    
  public ASTRelationalOperator(int id) {
    super(id);
  }

  public ASTRelationalOperator(Caesar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CaesarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    
}
