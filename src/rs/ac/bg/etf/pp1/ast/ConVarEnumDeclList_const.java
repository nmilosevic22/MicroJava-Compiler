// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class ConVarEnumDeclList_const extends ConVarEnumDeclList {

    private ConDeclList ConDeclList;
    private ConVarEnumDeclList ConVarEnumDeclList;

    public ConVarEnumDeclList_const (ConDeclList ConDeclList, ConVarEnumDeclList ConVarEnumDeclList) {
        this.ConDeclList=ConDeclList;
        if(ConDeclList!=null) ConDeclList.setParent(this);
        this.ConVarEnumDeclList=ConVarEnumDeclList;
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.setParent(this);
    }

    public ConDeclList getConDeclList() {
        return ConDeclList;
    }

    public void setConDeclList(ConDeclList ConDeclList) {
        this.ConDeclList=ConDeclList;
    }

    public ConVarEnumDeclList getConVarEnumDeclList() {
        return ConVarEnumDeclList;
    }

    public void setConVarEnumDeclList(ConVarEnumDeclList ConVarEnumDeclList) {
        this.ConVarEnumDeclList=ConVarEnumDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConDeclList!=null) ConDeclList.accept(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConDeclList!=null) ConDeclList.traverseTopDown(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConDeclList!=null) ConDeclList.traverseBottomUp(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConVarEnumDeclList_const(\n");

        if(ConDeclList!=null)
            buffer.append(ConDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConVarEnumDeclList!=null)
            buffer.append(ConVarEnumDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConVarEnumDeclList_const]");
        return buffer.toString();
    }
}
