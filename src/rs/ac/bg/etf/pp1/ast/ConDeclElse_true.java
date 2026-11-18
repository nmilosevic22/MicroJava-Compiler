// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class ConDeclElse_true extends ConDeclElse {

    private ConDecl ConDecl;
    private ConDeclElse ConDeclElse;

    public ConDeclElse_true (ConDecl ConDecl, ConDeclElse ConDeclElse) {
        this.ConDecl=ConDecl;
        if(ConDecl!=null) ConDecl.setParent(this);
        this.ConDeclElse=ConDeclElse;
        if(ConDeclElse!=null) ConDeclElse.setParent(this);
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConDecl!=null) ConDecl.accept(visitor);
        if(ConDeclElse!=null) ConDeclElse.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConDecl!=null) ConDecl.traverseTopDown(visitor);
        if(ConDeclElse!=null) ConDeclElse.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConDecl!=null) ConDecl.traverseBottomUp(visitor);
        if(ConDeclElse!=null) ConDeclElse.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConDeclElse_true(\n");

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
        buffer.append(") [ConDeclElse_true]");
        return buffer.toString();
    }
}
