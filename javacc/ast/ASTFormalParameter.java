/* Generated By:JJTree: Do not edit this line. ASTFormalParameter.java */

public class ASTFormalParameter extends SimpleNode {
  public ASTFormalParameter(int id) {
    super(id);
  }

  public ASTFormalParameter(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}