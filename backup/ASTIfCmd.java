/* Generated By:JJTree: Do not edit this line. ASTIfCmd.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package caesar;

public
class ASTIfCmd extends SimpleNode {
  public ASTIfCmd(int id) {
    super(id);
  }

  public ASTIfCmd(Caesar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CaesarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a58b2351defd775977cc51cebeb9748f (do not edit this line) */
