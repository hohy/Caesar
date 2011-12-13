package caesar.ast;

/**
 *
 * @author hohy
 */
public interface TreeVisitor {

    public void visit(BinaryTree aThis);

    //public void visit(IdentifierTree aThis);
    
    public void visit(LiteralTree aThis);
    
    public void visit(PrintlnTree aThis);
    
    public void visit(AssignVariableTree aThis);
    
    public void visit(CreateVariableTree aThis);
    
    public void visit(CommandListTree aThis);
    
    public void visit(ProgramTree aThis);
    
    public void visit(IfTree aThis);
    
    public void visit(ClassDefinitionTree aThis);
        
    public void visit(ClassIdentifierTree aThis);

    public void visitMethodIdentifier(MethodIdentifierTree aThis);
    
    public void visit(ClassMethodIdentifierTree aThis);
    
    public void visit(FieldIdentifierTree aThis);
    
    public void visit(MethodDefinitionTree aThis);

    public void visit(MethodCallTree methodCallTree);

    public void visit(ReturnTree returnTree);
}
