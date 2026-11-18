// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class ConDeclList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private ConDecl ConDecl;
    private ConDeclElse ConDeclElse;

    public ConDeclList (Type Type, ConDecl ConDecl, ConDeclElse ConDeclElse) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConDecl=ConDecl;
        if(ConDecl!=null) ConDecl.setParent(this);
        this.ConDeclElse=ConDeclElse;
        if(ConDeclElse!=null) ConDeclElse.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConDecl getConDecl() {
        return ConDecl;
    }

    public void setConDecl(ConDecl ConDecl) {
        this.ConDecl=ConDecl;
    }

    public ConDeclElse getConDeclElse() {
        return ConDeclElse;
    }

    public void setConDeclElse(ConDeclElse ConDeclElse) {
        this.ConDeclElse=ConDeclElse;
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
        if(Type!=null) Type.accept(visitor);
        if(ConDecl!=null) ConDecl.accept(visitor);
        if(ConDeclElse!=null) ConDeclElse.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConDecl!=null) ConDecl.traverseTopDown(visitor);
        if(ConDeclElse!=null) ConDeclElse.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConDecl!=null) ConDecl.traverseBottomUp(visitor);
        if(ConDeclElse!=null) ConDeclElse.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConDeclList(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConDecl!=null)
            buffer.append(ConDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConDeclElse!=null)
            buffer.append(ConDeclElse.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConDeclList]");
        return buffer.toString();
    }
}
