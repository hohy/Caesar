/* Generated By:JJTree: Do not edit this line. ASTExpressionX.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package caesar;

public
class ASTExpressionX extends SimpleNode {
  public ASTExpressionX(int id) {
    super(id);
  }

  public ASTExpressionX(Caesar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CaesarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ca2407b9d5691f6fc01d489c838e7df0 (do not edit this line) */
