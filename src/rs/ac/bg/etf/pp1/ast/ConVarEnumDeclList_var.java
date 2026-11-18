// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class ConVarEnumDeclList_var extends ConVarEnumDeclList {

    private VarDeclList VarDeclList;
    private ConVarEnumDeclList ConVarEnumDeclList;

    public ConVarEnumDeclList_var (VarDeclList VarDeclList, ConVarEnumDeclList ConVarEnumDeclList) {
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.ConVarEnumDeclList=ConVarEnumDeclList;
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.setParent(this);
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
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
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConVarEnumDeclList_var(\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConVarEnumDeclList!=null)
            buffer.append(ConVarEnumDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConVarEnumDeclList_var]");
        return buffer.toString();
    }
}
