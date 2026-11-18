// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Method Method;
    private VarDeclListSingle VarDeclListSingle;
    private StatementList StatementList;

    public MethodDecl (Method Method, VarDeclListSingle VarDeclListSingle, StatementList StatementList) {
        this.Method=Method;
        if(Method!=null) Method.setParent(this);
        this.VarDeclListSingle=VarDeclListSingle;
        if(VarDeclListSingle!=null) VarDeclListSingle.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public Method getMethod() {
        return Method;
    }

    public void setMethod(Method Method) {
        this.Method=Method;
    }

    public VarDeclListSingle getVarDeclListSingle() {
        return VarDeclListSingle;
    }

    public void setVarDeclListSingle(VarDeclListSingle VarDeclListSingle) {
        this.VarDeclListSingle=VarDeclListSingle;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Method!=null) Method.accept(visitor);
        if(VarDeclListSingle!=null) VarDeclListSingle.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Method!=null) Method.traverseTopDown(visitor);
        if(VarDeclListSingle!=null) VarDeclListSingle.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Method!=null) Method.traverseBottomUp(visitor);
        if(VarDeclListSingle!=null) VarDeclListSingle.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(Method!=null)
            buffer.append(Method.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclListSingle!=null)
            buffer.append(VarDeclListSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
