// generated with ast extension for cup
// version 0.8
// 3/8/2026 17:29:6


package rs.ac.bg.etf.pp1.ast;

public class ConVarEnumDeclList_enum extends ConVarEnumDeclList {

    private EnumDeclList EnumDeclList;
    private ConVarEnumDeclList ConVarEnumDeclList;

    public ConVarEnumDeclList_enum (EnumDeclList EnumDeclList, ConVarEnumDeclList ConVarEnumDeclList) {
        this.EnumDeclList=EnumDeclList;
        if(EnumDeclList!=null) EnumDeclList.setParent(this);
        this.ConVarEnumDeclList=ConVarEnumDeclList;
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.setParent(this);
    }

    public EnumDeclList getEnumDeclList() {
        return EnumDeclList;
    }

    public void setEnumDeclList(EnumDeclList EnumDeclList) {
        this.EnumDeclList=EnumDeclList;
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
        if(EnumDeclList!=null) EnumDeclList.accept(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumDeclList!=null) EnumDeclList.traverseTopDown(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumDeclList!=null) EnumDeclList.traverseBottomUp(visitor);
        if(ConVarEnumDeclList!=null) ConVarEnumDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConVarEnumDeclList_enum(\n");

        if(EnumDeclList!=null)
            buffer.append(EnumDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConVarEnumDeclList!=null)
            buffer.append(ConVarEnumDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConVarEnumDeclList_enum]");
        return buffer.toString();
    }
}
